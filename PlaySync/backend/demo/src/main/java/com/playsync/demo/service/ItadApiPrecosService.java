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
import com.playsync.demo.Entities.ItadShop;
import com.playsync.demo.client.PriceClientItad;
import com.playsync.demo.dtoresponse.DrmItadResponse;
import com.playsync.demo.dtoresponse.ItadDealsDto;
import com.playsync.demo.dtoresponse.ItadMainClassDto;
import com.playsync.demo.repository.ItadMainClassRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItadApiPrecosService {

    private final ItadMainClassRepository itadMainClassRepository;

    private final PriceClientItad priceClientItad;

    public List<ItadMainClassDto> principalMethod(List<String> ids) {
        List<ItadMainClass> itadMainClass = this.itadMainClassRepository.findByIds(ids);

        if (itadMainClass.isEmpty()) {

        }
    }

    public List<ItadMainClassDto> callApi(List<String> ids) {
        return this.priceClientItad.buscarPrecos(ids).block();
    }

    private void persistDataOfApiInDatabase(List<ItadMainClassDto> itadMainClassDtos) {
        List<ItadMainClass> itadMainClasses = new ArrayList<>();
        for (ItadMainClassDto itadMainClassDto : itadMainClassDtos) {
            ItadMainClass itadMainClass = new ItadMainClass(itadMainClassDto.getIdGame(), LocalDateTime.now());
            for (ItadDealsDto itadDealsDto : itadMainClassDto.getDeals()) {
                ItadDeals itadDeals = new ItadDeals(itadMainClass, itadDealsDto.getCut());
                if (!itadDealsDto.getDrm().isEmpty()) {
                    for (DrmItadResponse drmItadResponse : itadDealsDto.getDrm()) {
                        ItadDrm itadDrm = new ItadDrm(drmItadResponse.getId(), drmItadResponse.getName(), null);
                        
                    }
                }
            }
        }
    }

}
