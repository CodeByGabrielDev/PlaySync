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
import com.playsync.demo.Entities.RawgApiBuscaTermo;
import com.playsync.demo.client.RawgClient;
import com.playsync.demo.client.SteamClient;
import com.playsync.demo.dtoresponse.BuscaPorTermoDTO;
import com.playsync.demo.dtoresponse.ItensFiltradosPeloTermoDTO;
import com.playsync.demo.dtoresponse.PrecoDeItensDTO;
import com.playsync.demo.dtoresponse.TotalItensBuscadosRawgDTO;
import com.playsync.demo.enums.ControllerSupport;
import com.playsync.demo.repository.BuscaPorTermoRepository;
import com.playsync.demo.repository.ItensBuscadosPeloTermoRepository;
import com.playsync.demo.repository.PrecoPorJogoRepository;
import com.playsync.demo.repository.RawgApiBuscaTermoRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Service
public class RawgApiService {
   
    private final RawgClient rawgClient;


	
    public TotalItensBuscadosRawgDTO buscarItensNaApi(String nomeJogo){
        TotalItensBuscadosRawgDTO totalItensBuscadosRawgDTO = this.rawgClient.buscarPorTermoRawg(nomeJogo).block();

        if(totalItensBuscadosRawgDTO == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND," Nao foi encontrado itens por esse termo");
        }
        return totalItensBuscadosRawgDTO;
    }
	
/* 
	private Boolean validacao(List<ItensBuscadorPeloTermo> lista) {
		LocalDateTime dataLimite = LocalDateTime.now().minusSeconds(10);
		for (ItensBuscadorPeloTermo i : lista) {
			if (i.getDataPesquisaUsuario().isBefore(dataLimite)) {
				return true;
			}
		}
		return false;
	}
*/
	


	
}
