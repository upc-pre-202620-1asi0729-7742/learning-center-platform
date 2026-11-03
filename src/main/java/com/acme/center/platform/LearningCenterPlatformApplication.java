package com.acme.center.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Bootstrap class for the Learning Center Platform application.
 *
 * <p>Initializes Spring Boot auto-configuration and JPA auditing infrastructure.</p>
 */
@EnableJpaAuditing
@SpringBootApplication
public class LearningCenterPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(LearningCenterPlatformApplication.class, args);
    }

}
