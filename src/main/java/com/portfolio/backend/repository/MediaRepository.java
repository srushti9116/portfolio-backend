package com.portfolio.backend.repository;

import com.portfolio.backend.entity.Media;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaRepository extends JpaRepository<Media, Long> {
}