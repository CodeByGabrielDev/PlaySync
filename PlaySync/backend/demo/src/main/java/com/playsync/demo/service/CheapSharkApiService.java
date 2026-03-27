package com.playsync.demo.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.playsync.demo.Entities.CheapSharkJogosEPrecosApi;
import com.playsync.demo.Entities.CheapSharkLojasApi;
import com.playsync.demo.Entities.RawgApiBuscaTermo;
import com.playsync.demo.client.CheapSharkClient;
import com.playsync.demo.dtoresponse.CheapSharkApiDto;
import com.playsync.demo.dtoresponse.CheapSharkApiStoresDto;
import com.playsync.demo.dtoresponse.LojaESeusPrecosResponseCheapShark;
import com.playsync.demo.dtoresponse.MergerCheapSharkGamesAndStores;
import com.playsync.demo.dtoresponse.RawgApiBuscaTermoDTO;
import com.playsync.demo.repository.CheapSharkJogosEPrecosApiRepository;
import com.playsync.demo.repository.CheapSharkLojasApiRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CheapSharkApiService {

    private final CheapSharkClient cheapSharkClient;
    private final CheapSharkLojasApiRepository cheapSharkLojasApiRepository;
    private final CheapSharkJogosEPrecosApiRepository cheapSharkJogosEPrecosApiRepository;
    private final CotacaoDolarService cotacaoDolarService;

    public List<MergerCheapSharkGamesAndStores> principalMethod(String termo) {
        List<CheapSharkJogosEPrecosApi> cheapSharkApiBanco = this.cheapSharkJogosEPrecosApiRepository
                .selectByTerm(termo);
        List<CheapSharkLojasApi> cheapSharkLojasApis = validaBancoDeDados();
        if (cheapSharkApiBanco == null || cheapSharkApiBanco.isEmpty()) {
            return validaTermo(termo, cheapSharkLojasApis);
        }
        return validaINformacaoNoBancoDeDados(cheapSharkApiBanco, termo, cheapSharkLojasApis);
    }

    public List<CheapSharkApiDto> pegarInformacoesNaApi(String termo) {
        return this.cheapSharkClient.buscarPrecoCheapShark(termo).block();
    }

    public List<MergerCheapSharkGamesAndStores> validaINformacaoNoBancoDeDados(
            List<CheapSharkJogosEPrecosApi> listaDeEntidadesNoBanco, String termo,
            List<CheapSharkLojasApi> cheapSharkLojasApis) {
        List<CheapSharkJogosEPrecosApi> listaDeDesatualizados = validaItensDesatualizados(listaDeEntidadesNoBanco);
        if (listaDeDesatualizados != null && !listaDeDesatualizados.isEmpty()) {
            List<CheapSharkApiDto> buscaApi = pegarInformacoesNaApi(termo);
            Map<String, CheapSharkApiDto> mapaApi = new HashMap<>();

            for (CheapSharkApiDto dto : buscaApi) {
                mapaApi.put(dto.getNomeJogo().toLowerCase(), dto);
            }

            for (CheapSharkJogosEPrecosApi entidade : listaDeDesatualizados) {
                CheapSharkApiDto dto = mapaApi.get(entidade.getNomeJogo().toLowerCase());
                if (dto != null) {
                    entidade.setDataLastSearch(LocalDateTime.now());
                    entidade.setDesconto(dto.getDesconto());
                    entidade.setNomeJogo(dto.getNomeJogo());
                    entidade.setPrecoAtual(dto.getPrecoAtual());
                    entidade.setPrecoOriginal(dto.getPrecoOriginal());

                }
            }
            this.cheapSharkJogosEPrecosApiRepository.saveAll(listaDeDesatualizados);
        }
        return mergearApisCheapShark(listaDeEntidadesNoBanco, cheapSharkLojasApis);

    }

    public List<CheapSharkJogosEPrecosApi> validaItensDesatualizados(
            List<CheapSharkJogosEPrecosApi> listaDeEntidadesNoBanco) {
        List<CheapSharkJogosEPrecosApi> listaDeItensDesatualizados = new ArrayList<>();
        for (CheapSharkJogosEPrecosApi cheapSharkJogosEPrecosApi : listaDeEntidadesNoBanco) {
            LocalDateTime dataLimite = LocalDateTime.now().minusSeconds(10);
            if (cheapSharkJogosEPrecosApi.getDataLastSearch().isBefore(dataLimite)) {
                listaDeItensDesatualizados.add(cheapSharkJogosEPrecosApi);
            }
        }
        return listaDeItensDesatualizados;
    }

    @Transactional
    public List<MergerCheapSharkGamesAndStores> validaTermo(String termo,
            List<CheapSharkLojasApi> cheapSharkLojasApis) {
        List<CheapSharkApiDto> cheapSharkApiDtos = pegarInformacoesNaApi(termo);
        List<CheapSharkJogosEPrecosApi> cheapSharkJogosEPrecosApis = new ArrayList<>();

        Map<Long, CheapSharkLojasApi> mapLojas = new HashMap<>();
        for (CheapSharkLojasApi cheapSharkLojasApi : cheapSharkLojasApis) {
            mapLojas.put(cheapSharkLojasApi.getId(), cheapSharkLojasApi);
        }
        if (cheapSharkApiDtos != null && !cheapSharkApiDtos.isEmpty()) {
            for (CheapSharkApiDto cheapSharkApiDto : cheapSharkApiDtos) {
                CheapSharkJogosEPrecosApi cheapSharkJogosEPrecosApi = new CheapSharkJogosEPrecosApi(
                        cheapSharkApiDto.getNomeJogo(),
                        cheapSharkApiDto.getPrecoAtual(), cheapSharkApiDto.getPrecoOriginal(),
                        cheapSharkApiDto.getDesconto(), cheapSharkApiDto.getStoreId(), null, LocalDateTime.now());
                CheapSharkLojasApi cheapSharkLojasApi = mapLojas.get(cheapSharkApiDto.getStoreId());
                if (cheapSharkLojasApi != null) {
                    cheapSharkJogosEPrecosApi.setCheapSharkLojasApi(cheapSharkLojasApi);
                    cheapSharkLojasApi.getCheapSharkJogosEPrecos().add(cheapSharkJogosEPrecosApi);
                    cheapSharkJogosEPrecosApis.add(cheapSharkJogosEPrecosApi);
                }

            }
            this.cheapSharkJogosEPrecosApiRepository.saveAll(cheapSharkJogosEPrecosApis);
            return mergearApisCheapShark(cheapSharkJogosEPrecosApis, cheapSharkLojasApis);
            /*
             * o sistema
             */
        }
        System.out.println(cheapSharkApiDtos);
        return new ArrayList<>();
    }

    public List<CheapSharkApiStoresDto> pegarLojas() {
        return this.cheapSharkClient.buscarLojasCheapShark().block();
    }

    @Transactional
    public List<CheapSharkLojasApi> validaBancoDeDados() {
        List<CheapSharkLojasApi> buscaNoBanco = this.cheapSharkLojasApiRepository.consultarTabela();
        if (buscaNoBanco == null || buscaNoBanco.isEmpty()) {
            buscaNoBanco = persisteInformacaoNoBancoDeDados(pegarLojas());
            return buscaNoBanco;
        }
        return buscaNoBanco;
    }

    private List<CheapSharkLojasApi> persisteInformacaoNoBancoDeDados(List<CheapSharkApiStoresDto> listaVindoDaApi) {
        List<CheapSharkLojasApi> listaDeEntidades = new ArrayList<>();
        for (CheapSharkApiStoresDto cheapSharkApiDto : listaVindoDaApi) {
            if (cheapSharkApiDto.getEstaAtivoOuNao().equals(1)) {
                listaDeEntidades
                        .add(new CheapSharkLojasApi(cheapSharkApiDto.getIdLoja(), cheapSharkApiDto.getNomeLoja()));
            }
        }
        this.cheapSharkLojasApiRepository.saveAll(listaDeEntidades);
        return listaDeEntidades;
    }

    public List<CheapSharkApiDto> montaDto(List<CheapSharkJogosEPrecosApi> lista) {
        List<CheapSharkApiDto> listaDto = new ArrayList<>();

        for (CheapSharkJogosEPrecosApi cheapSharkJogosEPrecosApi : lista) {
            listaDto.add(new CheapSharkApiDto(cheapSharkJogosEPrecosApi.getNomeJogo(),
                    cheapSharkJogosEPrecosApi.getPrecoAtual(), cheapSharkJogosEPrecosApi.getPrecoOriginal(),
                    cheapSharkJogosEPrecosApi.getDesconto(), cheapSharkJogosEPrecosApi.getStoreId()));
        }
        return listaDto;
    }

    private List<MergerCheapSharkGamesAndStores> mergearApisCheapShark(List<CheapSharkJogosEPrecosApi> listaDeJogos,
            List<CheapSharkLojasApi> cheapSharkLojasApis) {
        Map<Long, CheapSharkLojasApi> mapperLojas = new HashMap<>();
        for (CheapSharkLojasApi cheapSharkLojasApi : cheapSharkLojasApis) {
            mapperLojas.put(cheapSharkLojasApi.getIdLoja(), cheapSharkLojasApi);
        }
        Map<String, MergerCheapSharkGamesAndStores> mapperJogos = new HashMap<>();
        for (CheapSharkJogosEPrecosApi cheapSharkJogosEPrecosApi : listaDeJogos) {
            CheapSharkLojasApi cheapSharkLojasApi = mapperLojas.get(cheapSharkJogosEPrecosApi.getStoreId());
            String nomeJogo = cheapSharkJogosEPrecosApi.getNomeJogo();
            mapperJogos.putIfAbsent(nomeJogo, new MergerCheapSharkGamesAndStores(nomeJogo, new ArrayList<>()));
            MergerCheapSharkGamesAndStores response = mapperJogos.get(cheapSharkJogosEPrecosApi.getNomeJogo());
            if (cheapSharkLojasApi != null) {
                LojaESeusPrecosResponseCheapShark lojaESeusPrecosResponseCheapShark = new LojaESeusPrecosResponseCheapShark(
                        cheapSharkJogosEPrecosApi.getPrecoAtual(), cheapSharkJogosEPrecosApi.getPrecoOriginal(),
                        cheapSharkJogosEPrecosApi.getDesconto(), cheapSharkJogosEPrecosApi.getNomeJogo(),
                        cheapSharkJogosEPrecosApi.getStoreId(), cheapSharkLojasApi.getNomeLoja());
                response.getLojaESeusPrecosResponseCheapSharks().add(lojaESeusPrecosResponseCheapShark);
            }

        }
        return new ArrayList<>(mapperJogos.values());
    }

}
