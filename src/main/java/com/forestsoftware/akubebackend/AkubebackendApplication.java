package com.forestsoftware.akubebackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class AkubebackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(AkubebackendApplication.class, args);
    }

}
