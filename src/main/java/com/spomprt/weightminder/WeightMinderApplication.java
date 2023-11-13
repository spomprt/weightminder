package com.spomprt.weightminder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableFeignClients
@EnableScheduling
@SpringBootApplication
public class WeightMinderApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeightMinderApplication.class, args);
    }

}
