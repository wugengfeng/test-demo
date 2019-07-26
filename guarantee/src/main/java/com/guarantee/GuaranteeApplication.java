package com.guarantee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class GuaranteeApplication {

    public static void main(String[] args) {
        SpringApplication.run(GuaranteeApplication.class, args);
    }

}
