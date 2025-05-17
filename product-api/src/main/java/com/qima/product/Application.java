package com.qima.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for the Product API.
 * This class serves as the entry point for the Spring Boot application.
 * It is responsible for bootstrapping the application and starting the embedded
 * server.
 */
@SpringBootApplication
public class Application {
    /**
     * Main method to run the Spring Boot application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}