package com.crop.goodcrop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class GoodCropApplication {
    public static void main(String[] args) {
        SpringApplication.run(GoodCropApplication.class, args);
    }
}
