package com.playsync.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.playsync.demo.dtoresponse.CheapSharkApiStoresDto;
import com.playsync.demo.service.CheapSharkApiService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api-playsync")
@RestController
@RequiredArgsConstructor
public class BuscarLojasCheapSharkApiController {

    private final CheapSharkApiService cheapSharkApiService;

    @GetMapping("/get-stores")
    public List<CheapSharkApiStoresDto> buscarLojasCheapShark() {
        return this.cheapSharkApiService.pegarLojas();
    }
}
