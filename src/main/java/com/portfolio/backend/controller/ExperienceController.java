package com.portfolio.backend.controller;

import com.portfolio.backend.entity.Experience;
import com.portfolio.backend.repository.ExperienceRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/experience")
public class ExperienceController {

    private final ExperienceRepository experienceRepository;

    public ExperienceController(ExperienceRepository experienceRepository) {
        this.experienceRepository = experienceRepository;
    }

    @GetMapping
    public List<Experience> getAllExperience() {
        return experienceRepository.findAll();
    }

    @PostMapping
    public Experience createExperience(@RequestBody Experience experience) {
        return experienceRepository.save(experience);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Experience> updateExperience(
            @PathVariable Long id,
            @RequestBody Experience experience) {

        return experienceRepository.findById(id)
                .map(existingExperience -> {
                    existingExperience.setTitle(experience.getTitle());
                    existingExperience.setCompany(experience.getCompany());
                    existingExperience.setLocation(experience.getLocation());
                    existingExperience.setStartDate(experience.getStartDate());
                    existingExperience.setEndDate(experience.getEndDate());
                    existingExperience.setDescription(experience.getDescription());
                    existingExperience.setTechnologies(experience.getTechnologies());
                    existingExperience.setCurrent(experience.getCurrent());

                    return ResponseEntity.ok(
                            experienceRepository.save(existingExperience)
                    );
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExperience(@PathVariable Long id) {

        if (!experienceRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        experienceRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}