package com.spoon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.spoon.web.client")
public class SpoonacularApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpoonacularApplication.class, args);
    }

}
