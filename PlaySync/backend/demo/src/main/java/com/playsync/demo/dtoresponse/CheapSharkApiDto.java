package com.playsync.demo.dtoresponse;

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
public class CheapSharkApiDto {
    @JsonProperty("title")
    private String nomeJogo;
    @JsonProperty("salePrice")
    private Double precoAtual;
    @JsonProperty("normalPrice")
    private Double precoOriginal;
    @JsonProperty("savings")
    private Double desconto;
    @JsonProperty("storeID")
    private Long storeId;

}
