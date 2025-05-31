package com.example.lab8_gtics_20216352.service;


// ExpeditionService.java

import com.example.lab8_gtics_20216352.entity.Crewmember;
import com.example.lab8_gtics_20216352.entity.Expedition;
import com.example.lab8_gtics_20216352.repository.CrewmemberRepository;
import com.example.lab8_gtics_20216352.repository.ExpeditionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ExpeditionService {

    @Autowired
    private ExpeditionRepository expeditionRepository;

    @Autowired
    private CrewmemberRepository crewmemberRepository;

    public List<Expedition> getAllExpeditions() {
        return expeditionRepository.findAll();
    }

    public Optional<Expedition> getExpeditionById(Long id) {
        return expeditionRepository.findById(id);
    }

    @Transactional
    public boolean saveExpedition(Expedition expedition, Set<Long> crewMemberIds) {
        // Limpiar tripulación actual
        expedition.getCrewMembers().clear();

        // Verificar si los miembros están disponibles y agregarlos
        if (crewMemberIds != null) {
            for (Long crewId : crewMemberIds) {
                Crewmember crew = crewmemberRepository.findById(crewId).orElse(null);
                if (crew != null) {
                    // Verificar disponibilidad si la expedición está en planificación o en curso
                    if ((expedition.getEstado().equals("Planificada") ||
                            expedition.getEstado().equals("En Curso")) &&
                            !crew.isAvailableForExpedition() &&
                            !expedition.getCrewMembers().contains(crew)) {
                        return false; // Miembro no disponible
                    }
                    expedition.getCrewMembers().add(crew);
                }
            }
        }

        // Si intenta cambiar a "En Curso", verificar requisitos de tripulación
        if ("En Curso".equals(expedition.getEstado()) && !expedition.hasRequiredCrew()) {
            return false;
        }

        expeditionRepository.save(expedition);
        return true;
    }

    @Transactional
    public boolean changeExpeditionStatus(Long id, String newStatus, String resultados) {
        Optional<Expedition> optExpedition = expeditionRepository.findById(id);

        if (optExpedition.isPresent()) {
            Expedition expedition = optExpedition.get();

            // Validaciones según el cambio de estado
            if ("En Curso".equals(newStatus) && !expedition.hasRequiredCrew()) {
                return false; // No cumple requisitos para lanzamiento
            }

            // Actualizar estado
            expedition.setEstado(newStatus);

            // Si se completa, actualizar resultados
            if ("Completada".equals(newStatus) && resultados != null) {
                expedition.setResultados(resultados);
            }

            // Si se cancela, liberar tripulación
            if ("Cancelada".equals(newStatus)) {
                expedition.getCrewMembers().clear();
            }

            expeditionRepository.save(expedition);
            return true;
        }

        return false;
    }

    public void deleteExpedition(Long id) {
        expeditionRepository.deleteById(id);
    }
}