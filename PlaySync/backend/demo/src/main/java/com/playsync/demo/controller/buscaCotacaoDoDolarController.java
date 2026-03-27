package com.playsync.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.playsync.demo.dtoresponse.CotacaoDolarResponse;
import com.playsync.demo.service.CotacaoDolarService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api-playsync/dollar-quote")
public class buscaCotacaoDoDolarController {

    private final CotacaoDolarService cotacaoDolarService;

    // teste de Api
    @GetMapping
    public CotacaoDolarResponse buscarCotacaoDolar() {
        return this.cotacaoDolarService.buscarCotacaoDolar();
    }

}
