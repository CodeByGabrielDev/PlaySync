package com.playsync.demo.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.playsync.demo.dtoresponse.BuscaPorTermoDTO;
import com.playsync.demo.service.ApiSteam;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api-playsync")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174"})
public class BuscaPorTermoController {

	private final ApiSteam api;

	@PostMapping("/search")
	public BuscaPorTermoDTO buscar(@RequestParam String termo) {
		long startTotal = System.currentTimeMillis();
		System.out.println("[BUSCA] Recebida requisicao de busca para termo: " + termo);
		
		long startBusca = System.currentTimeMillis();
		BuscaPorTermoDTO result = this.api.buscaPorTermo(termo);
		long endBusca = System.currentTimeMillis();
		
		System.out.println("[BUSCA] Tempo api.buscaPorTermo(): " + (endBusca - startBusca) + "ms");
		System.out.println("[BUSCA] Quantidade de itens: " + (result.getItens() != null ? result.getItens().size() : 0));
		
		long endTotal = System.currentTimeMillis();
		System.out.println("[BUSCA] Tempo TOTAL da requisicao: " + (endTotal - startTotal) + "ms");
		
		return result;
	}

}
