package com.example.lab8_gtics_20216352.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;

import java.time.LocalDateTime;

@Entity
@Table(name = "expeditions")
@Getter
@Setter
public class Expedition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre_mision", nullable = false)
    private String nombreMision;

    @ManyToOne(optional = false)
    @JoinColumn(name = "planeta_destino_id", nullable = false)
    private Planet planetaDestino;

    @Column(name = "fecha_lanzamiento", nullable = false)
    private LocalDateTime fechaLanzamiento;

    @Column(name = "estado", nullable = false)
    private String estado;

    @Column(name = "objetivos", columnDefinition = "TEXT")
    private String objetivos;

    @Column(name = "resultados", columnDefinition = "TEXT")
    private String resultados;

    @ManyToMany
    @JoinTable(
            name = "expedition_crew",
            joinColumns = @JoinColumn(name = "expedition_id"),
            inverseJoinColumns = @JoinColumn(name = "crew_member_id")
    )
    private Set<Crewmember> crewMembers;

    public boolean hasRequiredCrew() {
        boolean hasPilot = crewMembers.stream()
                .anyMatch(cm -> "Piloto".equals(cm.getEspecialidad()));
        boolean hasScientist = crewMembers.stream()
                .anyMatch(cm -> "Científico".equals(cm.getEspecialidad()));

        return hasPilot && hasScientist;
    }

}