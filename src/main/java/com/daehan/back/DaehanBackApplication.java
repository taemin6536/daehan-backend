package com.daehan.back;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.TimeZone;

@Slf4j
@SpringBootApplication
public class DaehanBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(DaehanBackApplication.class, args);
    }

    @Bean
    public ApplicationRunner logTimeZone() {
        return args -> {
            String defaultTimeZone = TimeZone.getDefault().getID();
            log.info(
                    "Default TimeZone: {}",
                    defaultTimeZone
            );
        };
    }


}
