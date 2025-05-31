package com.example.lab8_gtics_20216352.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Entity
@Table(name = "planets")
@Getter
@Setter
public class Planet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "tipo_planeta", length = 100)
    private String tipoPlaneta;

    @Column(name = "habitable")
    private Boolean habitable;

    @Column(name = "gravedad_relativa")
    private Double gravedadRelativa;

    @Column(name = "descripcion", length = 500)
    private String descripcion;
}
