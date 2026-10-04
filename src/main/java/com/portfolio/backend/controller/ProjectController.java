package com.portfolio.backend.controller;

import com.portfolio.backend.entity.Project;
import com.portfolio.backend.repository.ProjectRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectRepository projectRepository;

    public ProjectController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @GetMapping
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @PostMapping
    public Project createProject(@RequestBody Project project) {
        return projectRepository.save(project);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(
            @PathVariable Long id,
            @RequestBody Project project) {

        return projectRepository.findById(id)
                .map(existingProject -> {

                    existingProject.setTitle(project.getTitle());
                    existingProject.setDescription(project.getDescription());
                    existingProject.setImage(project.getImage());
                    existingProject.setTechnologies(project.getTechnologies());
                    existingProject.setLiveUrl(project.getLiveUrl());
                    existingProject.setGithubUrl(project.getGithubUrl());

                    return ResponseEntity.ok(
                            projectRepository.save(existingProject)
                    );
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(
            @PathVariable Long id) {

        if (!projectRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        projectRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}