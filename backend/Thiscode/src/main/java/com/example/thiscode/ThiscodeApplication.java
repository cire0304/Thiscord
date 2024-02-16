package com.example.thiscode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ThiscodeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThiscodeApplication.class, args);
    }

}
