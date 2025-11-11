package org.example.smartspring;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SmartSpringApplication {
    public static void main(String[] args) {

        Dotenv dotenv = Dotenv.load();
        System.setProperty("GMAIL_USERNAME", dotenv.get("GMAIL_USERNAME"));
        System.setProperty("GMAIL_APP_PASSWORD", dotenv.get("GMAIL_APP_PASSWORD"));

        SpringApplication.run(SmartSpringApplication.class, args);


    }
}
