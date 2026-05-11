package com.example.douyry_ahmed;

import com.example.douyry_ahmed.entities.AppUser;
import com.example.douyry_ahmed.repositories.AppUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Bean
    CommandLineRunner seed(AppUserRepository appUserRepository) {
        return args -> {
            if (appUserRepository.count() == 0) {
                appUserRepository.save(AppUser.builder()
                        .username("client")
                        .password("1234")
                        .build());

                appUserRepository.save(AppUser.builder()
                        .username("admin")
                        .password("1234")
                        .build());
            }
        };
    }
}
