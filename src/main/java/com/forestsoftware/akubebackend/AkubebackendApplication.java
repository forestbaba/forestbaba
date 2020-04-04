package com.forestsoftware.akubebackend;

import com.forestsoftware.akubebackend.property.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@EnableConfigurationProperties({
        FileStorageProperties.class
})
@SpringBootApplication
public class AkubebackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(AkubebackendApplication.class, args);
    }

}
