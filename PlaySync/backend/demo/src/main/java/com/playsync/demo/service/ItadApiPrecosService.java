package com.playsync.demo.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import com.playsync.demo.Entities.ItadDeals;
import com.playsync.demo.Entities.ItadDrm;
import com.playsync.demo.Entities.ItadMainClass;
import com.playsync.demo.Entities.ItadPlataforms;
import com.playsync.demo.client.PriceClientItad;
import com.playsync.demo.dtoresponse.DrmItadResponse;
import com.playsync.demo.dtoresponse.ItadDealsDto;
import com.playsync.demo.dtoresponse.ItadMainClassDto;
import com.playsync.demo.dtoresponse.ItadPlataformsDto;
import com.playsync.demo.dtoresponse.ItadPriceDto;
import com.playsync.demo.dtoresponse.ItadRegularDto;
import com.playsync.demo.dtoresponse.ItadShopDto;
import com.playsync.demo.repository.ItadMainClassRepository;

import jakarta.persistence.OneToMany;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItadApiPrecosService {

    private final ItadMainClassRepository itadMainClassRepository;
    private final PriceClientItad priceClientItad;

    public List<ItadMainClassDto> principalMethod(List<String> ids) {
        List<ItadMainClass> itadMainClass = this.itadMainClassRepository.findByIds(ids);

        if (itadMainClass.isEmpty()) {
            persistDataOfApiInDatabase(callApi(ids));
        }
        validDataInDatabase(itadMainClass, ids);

    }

    public List<ItadMainClassDto> callApi(List<String> ids) {
        return this.priceClientItad.buscarPrecos(ids).block();
    }

    private void validDataInDatabase(List<ItadMainClass> listaEntidadeNoBanco, List<String> ids) {
        List<ItadMainClass> listaDeVencidos = new ArrayList<>();
        LocalDateTime dataLimite = LocalDateTime.now().minusSeconds(10);

        for (ItadMainClass itadMainClass : listaEntidadeNoBanco) {
            if (itadMainClass.getDataLastSearch().isBefore(dataLimite)) {
                listaDeVencidos.add(itadMainClass);
            }

        }
        if (!listaDeVencidos.isEmpty()) {

        }
    }

    private void persistDataOfApiInDatabase(List<ItadMainClassDto> itadMainClassDtos) {
        List<ItadMainClass> itadMainClasses = new ArrayList<>();
        for (ItadMainClassDto itadMainClassDto : itadMainClassDtos) {
            ItadMainClass itadMainClass = new ItadMainClass(itadMainClassDto.getIdGame(), LocalDateTime.now());
            for (ItadDealsDto itadDealsDto : itadMainClassDto.getDeals()) {
                ItadDeals itadDeals = new ItadDeals(itadDealsDto.getPrice().getAmount(),
                        itadDealsDto.getRegular().getAmount(), itadDealsDto.getCut(), itadDealsDto.getShop().getId(),
                        itadDealsDto.getShop().getName());
                if (!itadDealsDto.getDrm().isEmpty()) {
                    insereInformacaoDrm(itadDeals, itadDealsDto);
                }
                if (!itadDealsDto.getPlataformas().isEmpty()) {
                    insereInformacaoPlataforms(itadDeals, itadDealsDto);
                }
                itadDeals.setItadMainClass(itadMainClass);
                itadMainClass.getItad_deals().add(itadDeals);
            }
            itadMainClasses.add(itadMainClass);
        }
        this.itadMainClassRepository.saveAll(itadMainClasses);
    }

    private void atualizaInformacaoVencida(List<ItadMainClass> listaDeVencidos,
            List<String> ids) {
        List<ItadMainClassDto> callApi = callApi(ids);
        Map<String, ItadMainClassDto> mapperEntity = new HashMap<>();
        for (ItadMainClassDto itadMainClassDto : callApi) {
            mapperEntity.put(itadMainClassDto.getIdGame(), itadMainClassDto);
        }
        for (ItadMainClass itadMainClass : listaDeVencidos) {
            ItadMainClassDto itadMainClassDto = mapperEntity.get(itadMainClass.getIdGame());
            itadMainClass.setDataLastSearch(LocalDateTime.now());
            if (itadMainClassDto == null) {
                continue;
            }
            itadMainClass.getItad_deals().clear();
            for (ItadDealsDto itadDealsDto : itadMainClassDto.getDeals()) {
                ItadDeals itadDeals = new ItadDeals(null, null, null, null, null);
                auxUpdateInformations(itadDealsDto, itadDeals);

                if (itadDealsDto.getDrm() != null && !itadDealsDto.getDrm().isEmpty()) {
                    insereInformacaoDrm(itadDeals, itadDealsDto);
                }
                if (itadDealsDto.getPlataformas() != null && !itadDealsDto.getPlataformas().isEmpty()) {
                    insereInformacaoPlataforms(itadDeals, itadDealsDto);
                }
                itadDeals.setItadMainClass(itadMainClass);
                itadMainClass.getItad_deals().add(itadDeals);
            }
        }
        this.itadMainClassRepository.saveAll(listaDeVencidos);

    }

    private void auxUpdateInformations(ItadDealsDto itadDealsDto, ItadDeals itadDeals) {
        if (itadDealsDto.getPrice() != null && itadDealsDto.getPrice().getAmount() != null) {
            itadDeals.setPrice(itadDealsDto.getPrice().getAmount());
        }
        if (itadDealsDto.getRegular() != null && itadDealsDto.getRegular().getAmount() != null) {
            itadDeals.setRegular(itadDealsDto.getRegular().getAmount());
        }
        if (itadDealsDto.getShop() != null && itadDealsDto.getShop().getId() != null) {
            itadDeals.setShopId(itadDealsDto.getShop().getId());
            if (itadDealsDto.getShop().getName() != null) {
                itadDeals.setShopName(itadDealsDto.getShop().getName());
            }
        }
        if (itadDealsDto.getCut() != null) {
            itadDeals.setDesconto(itadDealsDto.getCut());
        }

    }

    private void insereInformacaoDrm(ItadDeals itadDeals, ItadDealsDto itadDealsDto) {
        for (DrmItadResponse drmItadResponse : itadDealsDto.getDrm()) {
            ItadDrm itadDrm = new ItadDrm(drmItadResponse.getId(), drmItadResponse.getName(), null);
            itadDrm.setItadDeals(itadDeals);
            itadDeals.getDrms().add(itadDrm);
        }
    }

    private void insereInformacaoPlataforms(ItadDeals itadDeals, ItadDealsDto itadDealsDto) {
        for (ItadPlataformsDto itadPlataformsDto : itadDealsDto.getPlataformas()) {
            ItadPlataforms itadPlataforms = new ItadPlataforms(itadPlataformsDto.getId(), itadPlataformsDto.getName(),
                    null);
            itadPlataforms.setItadDeals(itadDeals);
            itadDeals.getPlatforms().add(itadPlataforms);
        }
    }

    private ItadMainClassDto montaDto(List<ItadMainClass> entidades) {
        List<ItadMainClassDto> itadMainClassDtos = new ArrayList<>();
        for (ItadMainClass itadMainClass : entidades) {
            ItadMainClassDto itadMainClassDto = new ItadMainClassDto(itadMainClass.getIdGame(), null);
            for (ItadDeals itadDeals : itadMainClass.getItad_deals()) {
                ItadDealsDto itadDealsDto = new ItadDealsDto();

            }
        }
    }

    private void auxMontaDto(ItadDeals itadDeals, ItadDealsDto itadDealsDto) {
        if (itadDeals != null) {
            ItadShopDto itadShopDto = new ItadShopDto();
            ItadPriceDto itadPriceDto = new ItadPriceDto();
            ItadRegularDto itadRegularDto = new ItadRegularDto();
            if (itadDeals.getDesconto() != null) {
                itadDealsDto.setCut(itadDeals.getDesconto());
            }
            if (itadDeals.getShopId() != null) {
                itadShopDto.setId(itadDeals.getShopId());
            }
            if (itadDeals.getShopName() != null) {
                itadShopDto.setName(itadDeals.getShopName());
            }
            if (itadDeals.getPrice() != null) {
                itadPriceDto.setAmount(itadDeals.getPrice());
            }
            if (itadDeals.getRegular() != null) {
                itadRegularDto.setAmount(itadDeals.getRegular());
            }
        }
    }

    private void validaInformacaoDrmParaRetornoDto(ItadDeals itadDeals, ItadDealsDto itadDealsDto) {
        if (itadDeals.getDrms() != null && !itadDeals.getDrms().isEmpty()) {
            List<DrmItadResponse> itadResponses = new ArrayList<>();
            DrmItadResponse drmItadResponse = new DrmItadResponse(null, null);
            for (ItadDrm itadDrm : itadDeals.getDrms()) {
                if (itadDrm.getIdDrm() != null) {
                    drmItadResponse.setId(itadDrm.getId());
                }
                if (itadDrm.getNome() != null) {
                    drmItadResponse.setName(itadDrm.getNome());
                }   
                
            }
        }
    }

}
