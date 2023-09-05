package com.adriandborsan.adminback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AdminbackApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminbackApplication.class, args);
    }

}
