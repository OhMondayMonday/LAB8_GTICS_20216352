package com.example.lab8_gtics_20216352.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "crew_members")
@Getter
@Setter
public class Crewmember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre_completo", nullable = false)
    private String nombreCompleto;

    @Column(name = "especialidad", nullable = false)
    private String especialidad;

    @Column(name = "rango", length = 100)
    private String rango;

    @Column(name = "fecha_contratacion", nullable = false)
    private LocalDate fechaContratacion;

    @ManyToMany(mappedBy = "crewMembers")
    private Set<Expedition> expeditions = new HashSet<>();


    public boolean isAvailableForExpedition() {
        return expeditions.stream()
                .noneMatch(e -> e.getEstado().equals("Planificada") ||
                        e.getEstado().equals("En Curso"));
    }

}