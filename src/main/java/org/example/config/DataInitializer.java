package org.example.config;

import lombok.RequiredArgsConstructor;
import org.example.entity.Role;
import org.example.entity.User;
import org.example.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public void run(String... args) {
        userRepository.findByLoginId("admin").orElseGet(() -> userRepository.save(User.builder()
                .loginId("admin")
                .password("admin1234")
                .name("관리자")
                .role(Role.ADMIN)
                .provider("local")
                .build()));
    }
}
