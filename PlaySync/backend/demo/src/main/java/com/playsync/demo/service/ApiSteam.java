package com.playsync.demo.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.playsync.demo.Entities.BuscaPorTermo;
import com.playsync.demo.Entities.ItensBuscadorPeloTermo;
import com.playsync.demo.Entities.PrecosJogos;
import com.playsync.demo.client.SteamClient;
import com.playsync.demo.dtoresponse.BuscaPorTermoDTO;
import com.playsync.demo.dtoresponse.ItensFiltradosPeloTermoDTO;
import com.playsync.demo.dtoresponse.PrecoDeItensDTO;
import com.playsync.demo.repository.BuscaPorTermoRepository;
import com.playsync.demo.repository.ItensBuscadosPeloTermoRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ApiSteam {

	private final SteamClient webConfig;
	private final ItensBuscadosPeloTermoRepository itensRepository;
	private final BuscaPorTermoRepository buscaPorTermoRepository;

	@Transactional
	public BuscaPorTermoDTO buscaPorTermo(String termo) {
		List<ItensBuscadorPeloTermo> itensNoBanco = this.itensRepository.findByName(termo);
		List<ItensFiltradosPeloTermoDTO> itensVindoDaApi = new ArrayList<>();
		if (itensNoBanco.isEmpty()) {
			return metodoChamaApiEPersiste(termo);
		}
		BuscaPorTermoDTO buscaDto = new BuscaPorTermoDTO(null, itensVindoDaApi);
		for (ItensBuscadorPeloTermo itens : itensNoBanco) {
			if (itens.getDataPesquisaUsuario().isAfter(LocalDateTime.now().minus(6, ChronoUnit.HOURS))) {
				for (PrecosJogos price : itens.getPrecos()) {
					itensVindoDaApi.add(new ItensFiltradosPeloTermoDTO(itens.getIdGame(), itens.getNome(),
							new PrecoDeItensDTO(price.getPrecoInicial(), price.getPrecoFinal()), itens.getImg(), null));
				}
			}
		}
		buscaDto.setItens(itensVindoDaApi);
		buscaDto.setQtdDeItensEncontrados(itensVindoDaApi.size());
		return buscaDto;

	}

	private BuscaPorTermoDTO metodoChamaApiEPersiste(String termo) {
		BuscaPorTermoDTO buscaDto = this.webConfig.buscarPorTermo(termo).block();

		if (buscaDto == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nao encontrado nenhuma informacao");
		}

		List<ItensBuscadorPeloTermo> listaDeItens = new ArrayList<>();
		BuscaPorTermo buscaPorTermo = new BuscaPorTermo(buscaDto.getItens().size());
		for (ItensFiltradosPeloTermoDTO itens : buscaDto.getItens()) {
			listaDeItens.add(new ItensBuscadorPeloTermo(itens.getIdGame(), itens.getName(), buscaPorTermo,
					itens.getImg(), false, LocalDateTime.now()));
		}
		this.buscaPorTermoRepository.save(buscaPorTermo);
		this.itensRepository.saveAll(listaDeItens);
		return buscaDto;
	}

}
