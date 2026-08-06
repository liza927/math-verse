package com.mathverse.core.config;

import com.mathverse.core.entity.Role;
import com.mathverse.core.entity.User;
import com.mathverse.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.findByEmail("teacher@mathverse.com").isEmpty()) {
            User teacher = new User();
            teacher.setEmail("teacher@mathverse.com");
            teacher.setPassword(passwordEncoder.encode("teacher123"));
            teacher.setRole(Role.TEACHER);
            userRepository.save(teacher);
        }
    }
}