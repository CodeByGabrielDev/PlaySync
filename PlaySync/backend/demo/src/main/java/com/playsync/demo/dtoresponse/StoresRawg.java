package com.playsync.demo.dtoresponse;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StoresRawg {
    @JsonProperty("store")
    private LojasRawgApiDTO lojasRawgApiDTO;

}
