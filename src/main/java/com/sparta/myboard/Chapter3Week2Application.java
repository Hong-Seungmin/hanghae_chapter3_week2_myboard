package com.sparta.myboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Chapter3Week2Application {

    public static void main(String[] args) {
        SpringApplication.run(Chapter3Week2Application.class, args);
    }

}
