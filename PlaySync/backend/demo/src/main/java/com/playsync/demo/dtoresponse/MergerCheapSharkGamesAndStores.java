package com.playsync.demo.dtoresponse;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MergerCheapSharkGamesAndStores {

    private String nomeJogo;
    List<LojaESeusPrecosResponseCheapShark> lojaESeusPrecosResponseCheapSharks = new ArrayList<>();
}
