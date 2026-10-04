package com.portfolio.backend.controller;

import com.portfolio.backend.entity.Testimonial;
import com.portfolio.backend.repository.TestimonialRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/testimonials")
public class TestimonialController {

    private final TestimonialRepository testimonialRepository;

    public TestimonialController(TestimonialRepository testimonialRepository) {
        this.testimonialRepository = testimonialRepository;
    }

    @GetMapping
    public List<Testimonial> getAllTestimonials() {
        return testimonialRepository.findAll();
    }

    @PostMapping
    public Testimonial createTestimonial(@RequestBody Testimonial testimonial) {
        return testimonialRepository.save(testimonial);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Testimonial> updateTestimonial(
            @PathVariable Long id,
            @RequestBody Testimonial testimonial) {

        return testimonialRepository.findById(id)
                .map(existingTestimonial -> {
                    existingTestimonial.setName(testimonial.getName());
                    existingTestimonial.setRole(testimonial.getRole());
                    existingTestimonial.setCompany(testimonial.getCompany());
                    existingTestimonial.setMessage(testimonial.getMessage());
                    existingTestimonial.setImage(testimonial.getImage());
                    existingTestimonial.setPublished(testimonial.getPublished());

                    return ResponseEntity.ok(
                            testimonialRepository.save(existingTestimonial)
                    );
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTestimonial(@PathVariable Long id) {

        if (!testimonialRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        testimonialRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}