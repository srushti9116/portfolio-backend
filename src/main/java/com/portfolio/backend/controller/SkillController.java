package com.portfolio.backend.controller;

import com.portfolio.backend.entity.Skill;
import com.portfolio.backend.repository.SkillRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/skills")
public class SkillController {

    private final SkillRepository skillRepository;

    public SkillController(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    @GetMapping
    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }

    @PostMapping
    public Skill createSkill(@RequestBody Skill skill) {
        return skillRepository.save(skill);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Skill> updateSkill(
            @PathVariable Long id,
            @RequestBody Skill skill) {

        return skillRepository.findById(id)
                .map(existingSkill -> {

                    existingSkill.setName(skill.getName());
                    existingSkill.setCategory(skill.getCategory());
                    existingSkill.setProficiency(skill.getProficiency());
                    existingSkill.setIcon(skill.getIcon());

                    return ResponseEntity.ok(
                            skillRepository.save(existingSkill)
                    );
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSkill(
            @PathVariable Long id) {

        if (!skillRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        skillRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}