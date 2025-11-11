package com.docpet.animalhospital;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EnableJpaAuditing
public class AnimalHospitalApp {

    public static void main(String[] args) {
        SpringApplication.run(AnimalHospitalApp.class, args);
    }
}

