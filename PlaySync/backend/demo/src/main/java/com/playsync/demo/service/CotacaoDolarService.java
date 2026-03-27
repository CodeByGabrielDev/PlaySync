package com.playsync.demo.service;

import org.springframework.stereotype.Service;

import com.playsync.demo.client.AwesomeApiCotacaoDolarClient;
import com.playsync.demo.dtoresponse.CotacaoDolarResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CotacaoDolarService {

    private final AwesomeApiCotacaoDolarClient awesomeApiCotacaoDolarClient;

    public CotacaoDolarResponse buscarCotacaoDolar() {
        return this.awesomeApiCotacaoDolarClient.buscarCotacaoDolar().block();
    }

    // metood para converter os valores, vou usar la no service do cheapshark
    public Double converterDolarParaReal(Double valor) {
        return buscarCotacaoDolar().getInformacoesDolarParaReal().getValor_dolar() * valor;
    }
}
