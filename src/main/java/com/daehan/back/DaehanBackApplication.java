package com.daehan.back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
@ComponentScan(basePackages = "com.daehan")
public class DaehanBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(DaehanBackApplication.class, args);
    }
}
