package com.example.lab8_gtics_20216352.service;


import com.example.lab8_gtics_20216352.entity.Planet;
import com.example.lab8_gtics_20216352.repository.PlanetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlanetService {

    @Autowired
    private PlanetRepository planetRepository;

    public List<Planet> getAllPlanets() {
        return planetRepository.findAll();
    }

    public Optional<Planet> getPlanetById(Long id) {
        return planetRepository.findById(id);
    }

    public Planet savePlanet(Planet planet) {
        return planetRepository.save(planet);
    }

    public void deletePlanet(Long id) {
        planetRepository.deleteById(id);
    }
}