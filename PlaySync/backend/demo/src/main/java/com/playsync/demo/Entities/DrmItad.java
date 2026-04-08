package com.playsync.demo.Entities;

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
@Getter
@Setter
@NoArgsConstructor
@Table(name = "drm_itad")
public class DrmItad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "id_drm")
    private Long idDrm;
    @Column(name = "name")
    private String nome;

    public DrmItad(Long idDrm, String nome) {
        this.idDrm = idDrm;
        this.nome = nome;
    }

}
