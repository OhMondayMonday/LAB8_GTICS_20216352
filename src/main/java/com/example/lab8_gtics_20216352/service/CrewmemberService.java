package com.example.lab8_gtics_20216352.service;

import com.example.lab8_gtics_20216352.entity.Crewmember;
import com.example.lab8_gtics_20216352.repository.CrewmemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CrewmemberService {

    @Autowired
    private CrewmemberRepository crewmemberRepository;

    public List<Crewmember> getAllCrewMembers() {
        return crewmemberRepository.findAll();
    }

    public List<Crewmember> getAvailableCrewMembers() {
        return crewmemberRepository.findAvailableCrewMembers();
    }

    public Optional<Crewmember> getCrewmemberById(Long id) {
        return crewmemberRepository.findById(id);
    }

    public Crewmember saveCrewmember(Crewmember crewmember) {
        return crewmemberRepository.save(crewmember);
    }

    public void deleteCrewmember(Long id) {
        crewmemberRepository.deleteById(id);
    }

    public boolean hasPilotAndScientist(List<Long> crewIds) {
        boolean hasPilot = false;
        boolean hasScientist = false;

        for (Long id : crewIds) {
            Optional<Crewmember> crewOpt = crewmemberRepository.findById(id);
            if (crewOpt.isPresent()) {
                Crewmember crew = crewOpt.get();
                if ("Piloto".equals(crew.getEspecialidad())) {
                    hasPilot = true;
                }
                if ("Científico".equals(crew.getEspecialidad())) {
                    hasScientist = true;
                }
            }
        }

        return hasPilot && hasScientist;
    }
}