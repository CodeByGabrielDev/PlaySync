package com.playsync.demo.dtoresponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LojaESeusPrecosResponseCheapShark {
    private Double precoAtual;
    private Double precoOriginal;
    private Double desconto;
    private String nomeJogo;
    private Long storeId;
    private String nomeLoja;

}
