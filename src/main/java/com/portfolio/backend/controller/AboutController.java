package com.portfolio.backend.controller;

import com.portfolio.backend.entity.About;
import com.portfolio.backend.repository.AboutRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/about")
public class AboutController {

    private final AboutRepository aboutRepository;

    public AboutController(AboutRepository aboutRepository) {
        this.aboutRepository = aboutRepository;
    }

    @GetMapping
    public About getAbout() {
        return aboutRepository.findAll()
                .stream()
                .findFirst()
                .orElse(null);
    }

    @PutMapping
    public About updateAbout(@RequestBody About about) {

        About existingAbout = aboutRepository.findAll()
                .stream()
                .findFirst()
                .orElse(null);

        if (existingAbout != null) {
            existingAbout.setName(about.getName());
            existingAbout.setBio(about.getBio());
            existingAbout.setProfileImage(about.getProfileImage());
            existingAbout.setLocation(about.getLocation());
            existingAbout.setEmail(about.getEmail());
            existingAbout.setPhone(about.getPhone());

            return aboutRepository.save(existingAbout);
        }

        return aboutRepository.save(about);
    }
}