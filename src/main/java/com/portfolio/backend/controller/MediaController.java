package com.portfolio.backend.controller;

import com.portfolio.backend.entity.Media;

import com.portfolio.backend.repository.MediaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import java.util.List;

@RestController
@RequestMapping("/upload")
public class MediaController {

    private final MediaRepository mediaRepository;

    private final Path uploadDirectory =
            Paths.get("uploads");

    public MediaController(MediaRepository mediaRepository) {
        this.mediaRepository = mediaRepository;
    }

    @PostMapping("/image")
    public ResponseEntity<Media> uploadImage(
            @RequestParam("file") MultipartFile file) {

        try {

            if (file.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            Files.createDirectories(uploadDirectory);

            String originalFileName = file.getOriginalFilename();

            String extension = "";

            if (originalFileName != null &&
                    originalFileName.contains(".")) {

                extension = originalFileName.substring(
                        originalFileName.lastIndexOf("."));
            }

            String storedFileName =
                    UUID.randomUUID() + extension;

            Path filePath =
                    uploadDirectory.resolve(storedFileName);

            Files.copy(
                    file.getInputStream(),
                    filePath,
                    StandardCopyOption.REPLACE_EXISTING
            );

            Media media = new Media();

            media.setFileName(storedFileName);
            media.setOriginalFileName(originalFileName);
            media.setFileType(file.getContentType());
            media.setFileSize(file.getSize());
            media.setFilePath(filePath.toString());
            media.setFileUrl("/uploads/" + storedFileName);

            Media savedMedia =
                    mediaRepository.save(media);

            return ResponseEntity.ok(savedMedia);

        } catch (IOException e) {

            return ResponseEntity.internalServerError().build();
        }
    }
    
    @GetMapping("/media")
    public ResponseEntity<List<Media>> getAllMedia() {
        return ResponseEntity.ok(mediaRepository.findAll());
    }
    @DeleteMapping("/media/{id}")
    public ResponseEntity<Void> deleteMedia(@PathVariable Long id) {
        try {
            Media media = mediaRepository.findById(id).orElse(null);

            if (media == null) {
                return ResponseEntity.notFound().build();
            }

            Path filePath = Paths.get(media.getFilePath());

            Files.deleteIfExists(filePath);

            mediaRepository.delete(media);

            return ResponseEntity.noContent().build();

        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
}