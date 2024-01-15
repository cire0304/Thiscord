package com.example.thiscode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableJpaAuditing
@EnableMongoRepositories
@SpringBootApplication
public class ThiscodeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThiscodeApplication.class, args);
    }

}
