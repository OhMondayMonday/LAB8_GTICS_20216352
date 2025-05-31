// PlanetController.java
package com.example.lab8_gtics_20216352.controller;

import com.example.lab8_gtics_20216352.entity.Planet;
import com.example.lab8_gtics_20216352.service.PlanetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/planets")
public class PlanetController {

    @Autowired
    private PlanetService planetService;

    @GetMapping
    public String listPlanets(Model model) {
        model.addAttribute("planets", planetService.getAllPlanets());
        return "planets/list";
    }

    @GetMapping("/new")
    public String newPlanetForm(Model model) {
        model.addAttribute("planet", new Planet());
        return "planets/form";
    }

    @GetMapping("/edit/{id}")
    public String editPlanetForm(@PathVariable Long id, Model model) {
        Optional<Planet> planet = planetService.getPlanetById(id);

        if (planet.isPresent()) {
            model.addAttribute("planet", planet.get());
            return "planets/form";
        }

        return "redirect:/planets";
    }

    @PostMapping("/save")
    public String savePlanet(@ModelAttribute Planet planet, RedirectAttributes redirectAttributes) {
        planetService.savePlanet(planet);
        redirectAttributes.addFlashAttribute("success", "Planeta guardado exitosamente");
        return "redirect:/planets";
    }

    @GetMapping("/view/{id}")
    public String viewPlanet(@PathVariable Long id, Model model) {
        Optional<Planet> planet = planetService.getPlanetById(id);

        if (planet.isPresent()) {
            model.addAttribute("planet", planet.get());
            return "planets/view";
        }

        return "redirect:/planets";
    }

    @GetMapping("/delete/{id}")
    public String deletePlanet(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        planetService.deletePlanet(id);
        redirectAttributes.addFlashAttribute("success", "Planeta eliminado exitosamente");
        return "redirect:/planets";
    }
}
