package com.playsync.demo.dtoresponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
public class CheapSharkApiStoresDto {
    @JsonProperty("storeID")
    private Long idLoja;
    @JsonProperty("storeName")
    private String nomeLoja;
    @JsonProperty("isActive")
    private Integer estaAtivoOuNao;
}
