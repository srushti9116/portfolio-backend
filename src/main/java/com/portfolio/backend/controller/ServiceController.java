package com.portfolio.backend.controller;

import com.portfolio.backend.entity.Service;
import com.portfolio.backend.repository.ServiceRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/services")
public class ServiceController {

    private final ServiceRepository serviceRepository;

    public ServiceController(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @GetMapping
    public List<Service> getAllServices() {
        return serviceRepository.findAll();
    }

    @PostMapping
    public Service createService(@RequestBody Service service) {
        return serviceRepository.save(service);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Service> updateService(
            @PathVariable Long id,
            @RequestBody Service service) {

        return serviceRepository.findById(id)
                .map(existingService -> {
                    existingService.setTitle(service.getTitle());
                    existingService.setDescription(service.getDescription());
                    existingService.setIcon(service.getIcon());
                    existingService.setTechnologies(service.getTechnologies());
                    existingService.setPublished(service.getPublished());

                    return ResponseEntity.ok(
                            serviceRepository.save(existingService)
                    );
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {

        if (!serviceRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        serviceRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}