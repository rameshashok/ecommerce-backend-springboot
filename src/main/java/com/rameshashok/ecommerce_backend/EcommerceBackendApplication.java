package com.rameshashok.ecommerce_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for the Ecommerce Backend API.
 * This Spring Boot application provides REST endpoints for user authentication,
 * product management, and order processing with JWT-based security.
 */
@SpringBootApplication
public class EcommerceBackendApplication {

	/**
	 * Main method to start the Spring Boot application.
	 * 
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(EcommerceBackendApplication.class, args);
	}

}
