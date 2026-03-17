package com.playsync.demo.Entities;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.Collate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "total_itens_buscados_rawg")
@Getter
@Setter
@NoArgsConstructor
public class TotalItensBuscadosRawg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "qtd_itens")
    private Integer quantidadeDeItens;
    @OneToMany(mappedBy = "totalItensBuscadosRawg")
    private List<RawgApiBuscaTermo>rawgApiBuscaTermo = new ArrayList<>();
    public TotalItensBuscadosRawg(Integer quantidadeDeItens) {
        this.quantidadeDeItens = quantidadeDeItens;
    }


    

}
