package com.project.studyplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class StudyplatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudyplatformApplication.class, args);
    }

}
