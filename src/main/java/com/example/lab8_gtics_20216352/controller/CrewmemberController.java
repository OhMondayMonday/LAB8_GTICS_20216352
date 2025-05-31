package com.example.lab8_gtics_20216352.controller;

import com.example.lab8_gtics_20216352.entity.Crewmember;
import com.example.lab8_gtics_20216352.service.CrewmemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/crewmembers")
public class CrewmemberController {

    @Autowired
    private CrewmemberService crewmemberService;

    // Obtener todos los miembros
    @GetMapping
    public List<Crewmember> getAllCrewmembers() {
        return crewmemberService.getAllCrewMembers();
    }

    // Obtener un miembro por ID
    @GetMapping("/{id}")
    public Optional<Crewmember> getCrewmemberById(@PathVariable Long id) {
        return crewmemberService.getCrewmemberById(id);
    }

    // Crear o actualizar un miembro
    @PostMapping
    public Crewmember saveOrUpdateCrewmember(@RequestBody Crewmember crewmember) {
        return crewmemberService.saveCrewmember(crewmember);
    }

    // Eliminar un miembro
    @DeleteMapping("/{id}")
    public void deleteCrewmember(@PathVariable Long id) {
        crewmemberService.deleteCrewmember(id);
    }
}