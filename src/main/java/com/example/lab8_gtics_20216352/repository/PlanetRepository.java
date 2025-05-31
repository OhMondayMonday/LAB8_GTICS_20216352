package com.example.lab8_gtics_20216352.repository;

import com.example.lab8_gtics_20216352.entity.Expedition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanetRepository extends JpaRepository<Expedition, Long> {
}
