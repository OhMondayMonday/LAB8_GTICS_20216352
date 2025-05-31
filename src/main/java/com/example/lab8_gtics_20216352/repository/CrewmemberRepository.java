package com.example.lab8_gtics_20216352.repository;

import com.example.lab8_gtics_20216352.entity.Crewmember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;


import java.util.List;

@Repository
public interface CrewmemberRepository extends JpaRepository<Crewmember, Long> {

    @Query(value = "SELECT cm.* FROM crew_members cm " +
            "LEFT JOIN expedition_crew ec ON cm.id = ec.crew_member_id " +
            "LEFT JOIN expeditions e ON ec.expedition_id = e.id " +
            "WHERE e.id IS NULL OR (e.estado != 'Planificada' AND e.estado != 'En Curso')",
            nativeQuery = true)
    List<Crewmember> findAvailableCrewMembers();

    List<Crewmember> findByEspecialidad(String especialidad);
}
