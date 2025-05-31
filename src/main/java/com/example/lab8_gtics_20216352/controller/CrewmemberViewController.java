package com.example.lab8_gtics_20216352.controller;

import com.example.lab8_gtics_20216352.entity.Crewmember;
import com.example.lab8_gtics_20216352.service.CrewmemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@Controller
@RequestMapping("/crewmembers")
public class CrewmemberViewController {

    @Autowired
    private CrewmemberService crewmemberService;

    // Mostrar lista de miembros
    @GetMapping
    public String getAllCrewmembers(Model model) {
        model.addAttribute("crewmembers", crewmemberService.getAllCrewMembers());
        return "crewmembers/list";
    }

    // Formulario para crear un nuevo miembro
    @GetMapping("/new")
    public String createCrewmemberForm(Model model) {
        model.addAttribute("crewmember", new Crewmember());
        return "crewmembers/form";
    }

    // Guardar/Actualizar Datos del Miembro
    @PostMapping
    public String saveCrewmember(@ModelAttribute Crewmember crewmember) {
        crewmemberService.saveCrewmember(crewmember);
        return "redirect:/crewmembers";
    }

    // Eliminar un miembro
    @GetMapping("/delete/{id}")
    public String deleteCrewmember(@PathVariable Long id) {
        crewmemberService.deleteCrewmember(id);
        return "redirect:/crewmembers";
    }
}