
package com.example.lab8_gtics_20216352.controller;

import com.example.lab8_gtics_20216352.entity.Crewmember;
import com.example.lab8_gtics_20216352.entity.Expedition;
import com.example.lab8_gtics_20216352.entity.Planet;
import com.example.lab8_gtics_20216352.service.CrewmemberService;
import com.example.lab8_gtics_20216352.service.ExpeditionService;
import com.example.lab8_gtics_20216352.service.PlanetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/expeditions")
public class ExpeditionController {

    @Autowired
    private ExpeditionService expeditionService;

    @Autowired
    private PlanetService planetService;

    @Autowired
    private CrewmemberService crewmemberService;

    @GetMapping
    public String listExpeditions(Model model) {
        model.addAttribute("expeditions", expeditionService.getAllExpeditions());
        return "expeditions/list";
    }

    @GetMapping("/new")
    public String newExpeditionForm(Model model) {
        model.addAttribute("expedition", new Expedition());
        model.addAttribute("planets", planetService.getAllPlanets());
        model.addAttribute("crewMembers", crewmemberService.getAvailableCrewMembers());
        return "expeditions/form";
    }

    @GetMapping("/edit/{id}")
    public String editExpeditionForm(@PathVariable Long id, Model model) {
        Optional<Expedition> expedition = expeditionService.getExpeditionById(id);

        if (expedition.isPresent()) {
            model.addAttribute("expedition", expedition.get());
            model.addAttribute("planets", planetService.getAllPlanets());

            // Para edición, incluir miembros ya asignados + disponibles
            List<Crewmember> availableCrewMembers = crewmemberService.getAvailableCrewMembers();
            availableCrewMembers.addAll(expedition.get().getCrewMembers());
            model.addAttribute("crewMembers", availableCrewMembers.stream().distinct().collect(Collectors.toList()));

            // Obtener IDs de tripulantes asignados para preselección en el formulario
            Set<Long> selectedCrewIds = expedition.get().getCrewMembers().stream()
                    .map(Crewmember::getId)
                    .collect(Collectors.toSet());
            model.addAttribute("selectedCrewIds", selectedCrewIds);

            return "expeditions/form";
        }

        return "redirect:/expeditions";
    }

    @PostMapping("/save")
    public String saveExpedition(
            @ModelAttribute Expedition expedition,
            @RequestParam(required = false) Set<Long> crewIds,
            @RequestParam Long planetId,
            RedirectAttributes redirectAttributes) {

        // Asignar planeta
        Optional<Planet> planet = planetService.getPlanetById(planetId);
        if (planet.isPresent()) {
            expedition.setPlanetaDestino(planet.get());
        }

        boolean success = expeditionService.saveExpedition(expedition, crewIds);

        if (!success) {
            redirectAttributes.addFlashAttribute("error",
                    "No se pudo guardar la expedición. Verifica los requisitos de tripulación o la disponibilidad de los miembros.");
            return "redirect:/expeditions/new";
        }

        redirectAttributes.addFlashAttribute("success", "Expedición guardada exitosamente");
        return "redirect:/expeditions";
    }

    @GetMapping("/view/{id}")
    public String viewExpedition(@PathVariable Long id, Model model) {
        Optional<Expedition> expedition = expeditionService.getExpeditionById(id);

        if (expedition.isPresent()) {
            model.addAttribute("expedition", expedition.get());
            return "expeditions/view";
        }

        return "redirect:/expeditions";
    }

    @GetMapping("/status/{id}")
    public String changeStatusForm(@PathVariable Long id, Model model) {
        Optional<Expedition> expedition = expeditionService.getExpeditionById(id);

        if (expedition.isPresent()) {
            model.addAttribute("expedition", expedition.get());
            return "expeditions/status";
        }

        return "redirect:/expeditions";
    }

    @PostMapping("/status/update")
    public String updateStatus(
            @RequestParam Long id,
            @RequestParam String newStatus,
            @RequestParam(required = false) String resultados,
            RedirectAttributes redirectAttributes) {

        boolean success = expeditionService.changeExpeditionStatus(id, newStatus, resultados);

        if (!success) {
            redirectAttributes.addFlashAttribute("error",
                    "No se pudo cambiar el estado. Verifica los requisitos de tripulación.");
            return "redirect:/expeditions/status/" + id;
        }

        redirectAttributes.addFlashAttribute("success", "Estado actualizado exitosamente");
        return "redirect:/expeditions";
    }

    @GetMapping("/delete/{id}")
    public String deleteExpedition(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        expeditionService.deleteExpedition(id);
        redirectAttributes.addFlashAttribute("success", "Expedición eliminada exitosamente");
        return "redirect:/expeditions";
    }
}