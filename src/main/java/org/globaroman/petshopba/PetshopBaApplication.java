package org.globaroman.petshopba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PetshopBaApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetshopBaApplication.class, args);
    }

}
