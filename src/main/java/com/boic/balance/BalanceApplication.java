package com.boic.balance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "jwtAuditorAware")
@EnableJpaRepositories("com.boic.balance")
@EnableScheduling
public class BalanceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BalanceApplication.class, args);
    }

}
