package com.stocksip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Stocksip Back End Application - Stocksip Platform
 *
 * @summary
 * This is the main class for the Stocksip Platform application.
 * It is responsible for bootstrapping the application and configuring the necessary components.
 * It enables JPA auditing and starts the Spring Boot application.
 *
 * @since 1.0
 */
@EnableJpaAuditing
@SpringBootApplication
public class StocksipBackEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(StocksipBackEndApplication.class, args);
    }

}
