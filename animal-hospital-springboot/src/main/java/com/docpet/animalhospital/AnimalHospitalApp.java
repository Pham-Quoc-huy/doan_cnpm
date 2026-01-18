package com.docpet.animalhospital;

import com.docpet.animalhospital.config.AiProperties;
import com.docpet.animalhospital.config.JwtProperties;
import com.docpet.animalhospital.config.SpringSecurityAuditorAware;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@EnableConfigurationProperties({AiProperties.class, JwtProperties.class})
public class AnimalHospitalApp {

    @Bean
    public AuditorAware<String> auditorProvider() {
        return new SpringSecurityAuditorAware();
    }

    public static void main(String[] args) {
        SpringApplication.run(AnimalHospitalApp.class, args);
    }
}

