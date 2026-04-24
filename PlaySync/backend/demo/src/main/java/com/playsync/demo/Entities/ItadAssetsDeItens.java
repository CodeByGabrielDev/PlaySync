package com.playsync.demo.Entities;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "itad_assets_de_itens")
@Getter
@Setter
@NoArgsConstructor
public class ItadAssetsDeItens {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String banner145;
    private String banner300;
    private String banner400;
    private String banner600;
    private String boxart;
    private String idGame;
    @OneToOne
    @JoinColumn(name = "id_itad_busca_por_termo")
    private ItadBuscaPorTermo itadBuscaPorTermo;
    public ItadAssetsDeItens(String banner145, String banner300, String banner400, String banner600, String boxart,
            String idGame, ItadBuscaPorTermo itadBuscaPorTermo) {
        this.banner145 = banner145;
        this.banner300 = banner300;
        this.banner400 = banner400;
        this.banner600 = banner600;
        this.boxart = boxart;
        this.idGame = idGame;
        this.itadBuscaPorTermo = itadBuscaPorTermo;
    }

    

}
