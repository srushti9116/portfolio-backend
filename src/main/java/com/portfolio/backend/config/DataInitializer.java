package com.portfolio.backend.config;

import com.portfolio.backend.entity.User;
import com.portfolio.backend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initializeAdminUser(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            String adminEmail = "admin@portfolio.com";
            String adminPassword = System.getenv("ADMIN_PASSWORD");

            if (!userRepository.existsByEmail(adminEmail)) {

                if (adminPassword == null || adminPassword.isBlank()) {
                    throw new IllegalStateException(
                            "ADMIN_PASSWORD environment variable is not set."
                    );
                }

                User admin = new User();

                admin.setName("Portfolio Admin");
                admin.setEmail(adminEmail);
                admin.setPassword(
                        passwordEncoder.encode(adminPassword)
                );
                admin.setRole("ADMIN");

                userRepository.save(admin);

                System.out.println(
                        "Default admin user created: " + adminEmail
                );
            }
        };
    }
}