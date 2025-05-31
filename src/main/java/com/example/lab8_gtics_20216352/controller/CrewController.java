// CrewController.java
package com.example.lab8_gtics_20216352.controller;

import com.example.lab8_gtics_20216352.entity.Crewmember;
import com.example.lab8_gtics_20216352.service.CrewmemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/crew")
public class CrewController {

    @Autowired
    private CrewmemberService crewmemberService;

    @GetMapping
    public String listCrewMembers(Model model) {
        model.addAttribute("crewMembers", crewmemberService.getAllCrewMembers());
        return "crew/list";
    }

    @GetMapping("/new")
    public String newCrewMemberForm(Model model) {
        model.addAttribute("crewMember", new Crewmember());
        return "crew/form";
    }

    @GetMapping("/edit/{id}")
    public String editCrewMemberForm(@PathVariable Long id, Model model) {
        Optional<Crewmember> crewMember = crewmemberService.getCrewmemberById(id);

        if (crewMember.isPresent()) {
            model.addAttribute("crewMember", crewMember.get());
            return "crew/form";
        }

        return "redirect:/crew";
    }

    @PostMapping("/save")
    public String saveCrewMember(@ModelAttribute Crewmember crewMember, RedirectAttributes redirectAttributes) {
        crewmemberService.saveCrewmember(crewMember);
        redirectAttributes.addFlashAttribute("success", "Miembro de tripulación guardado exitosamente");
        return "redirect:/crew";
    }

    @GetMapping("/view/{id}")
    public String viewCrewMember(@PathVariable Long id, Model model) {
        Optional<Crewmember> crewMember = crewmemberService.getCrewmemberById(id);

        if (crewMember.isPresent()) {
            model.addAttribute("crewMember", crewMember.get());
            return "crew/view";
        }

        return "redirect:/crew";
    }

    @GetMapping("/delete/{id}")
    public String deleteCrewMember(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        crewmemberService.deleteCrewmember(id);
        redirectAttributes.addFlashAttribute("success", "Miembro de tripulación eliminado exitosamente");
        return "redirect:/crew";
    }
}