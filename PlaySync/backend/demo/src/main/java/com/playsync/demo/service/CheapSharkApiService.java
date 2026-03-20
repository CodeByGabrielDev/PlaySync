package com.playsync.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.playsync.demo.Entities.CheapSharkLojasApi;
import com.playsync.demo.client.CheapSharkClient;
import com.playsync.demo.dtoresponse.CheapSharkApiDto;
import com.playsync.demo.dtoresponse.CheapSharkApiStoresDto;
import com.playsync.demo.repository.CheapSharkLojasApiRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CheapSharkApiService {

    private final CheapSharkClient cheapSharkClient;
    private final CheapSharkLojasApiRepository cheapSharkLojasApiRepository;

    // testes de requisicoes
    public List<CheapSharkApiDto> pegarInformacoesNaApi(String termo) {
        return this.cheapSharkClient.buscarPrecoCheapShark(termo).block();
    }

    // testes de requisicoes
    public List<CheapSharkApiStoresDto> pegarLojas() {
        return this.cheapSharkClient.buscarLojasCheapShark().block();
    }
    @Transactional
    public void validaBancoDeDados() {
        List<CheapSharkLojasApi> buscaNoBanco = this.cheapSharkLojasApiRepository.consultarTabela();
        if (buscaNoBanco == null || buscaNoBanco.isEmpty()) {
            persisteInformacaoNoBancoDeDados(pegarLojas());
        }
    }

    private void persisteInformacaoNoBancoDeDados(List<CheapSharkApiStoresDto> listaVindoDaApi) {
        List<CheapSharkLojasApi> listaDeEntidades = new ArrayList<>();
        for (CheapSharkApiStoresDto cheapSharkApiDto : listaVindoDaApi) {
            if (cheapSharkApiDto.getEstaAtivoOuNao().equals(1)) {
                listaDeEntidades
                        .add(new CheapSharkLojasApi(cheapSharkApiDto.getIdLoja(), cheapSharkApiDto.getNomeLoja()));
            }
        }
        this.cheapSharkLojasApiRepository.saveAll(listaDeEntidades);
    }


    

}
