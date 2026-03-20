package com.playsync.demo.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cheap_shark_lojas_api")
@NoArgsConstructor
@Getter
@Setter
public class CheapSharkLojasApi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "id_loja")
    private Long idLoja;
    @Column(name = "nome_loja")
    private String nomeLoja;

    public CheapSharkLojasApi(Long idLoja, String nomeLoja) {
        this.idLoja = idLoja;
        this.nomeLoja = nomeLoja;
    }

}
