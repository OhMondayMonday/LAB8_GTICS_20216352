// HomeController.java
package com.example.lab8_gtics_20216352.controller;

import com.example.lab8_gtics_20216352.entity.Expedition;
import com.example.lab8_gtics_20216352.entity.Planet;
import com.example.lab8_gtics_20216352.service.ExpeditionService;
import com.example.lab8_gtics_20216352.service.PlanetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private ExpeditionService expeditionService;

    @Autowired
    private PlanetService planetService;

    @GetMapping("/")
    public String home(Model model) {
        List<Expedition> expeditions = expeditionService.getAllExpeditions();
        List<Planet> planets = planetService.getAllPlanets();

        model.addAttribute("expeditions", expeditions);
        model.addAttribute("planets", planets);

        return "index";
    }
}
