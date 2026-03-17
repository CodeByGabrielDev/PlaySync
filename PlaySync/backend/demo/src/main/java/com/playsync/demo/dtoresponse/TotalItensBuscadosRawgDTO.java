package com.playsync.demo.dtoresponse;

import java.util.ArrayList;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TotalItensBuscadosRawgDTO {
    @JsonProperty("count")
    private Integer quantidadeDeItens;
    @JsonProperty("results")
    private List<RawgApiBuscaTermoDTO>rawgApiBuscaTermo = new ArrayList<>();
    
}
