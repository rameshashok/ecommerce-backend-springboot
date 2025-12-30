package com.rameshashok.ecommerce_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Data Transfer Object for JWT authentication response.
 * Contains the JWT token and user information returned after successful authentication.
 */
@Data
@AllArgsConstructor
public class JwtResponse {
    /** The JWT access token */
    private String token;
    /** Token type (always "Bearer") */
    private String type = "Bearer";
    /** User's unique identifier */
    private Long id;
    /** User's email address */
    private String email;
    /** User's first name */
    private String firstName;
    /** User's last name */
    private String lastName;
    
    /**
     * Constructor without token type (defaults to "Bearer").
     * 
     * @param token the JWT token
     * @param id the user's ID
     * @param email the user's email
     * @param firstName the user's first name
     * @param lastName the user's last name
     */
    public JwtResponse(String token, Long id, String email, String firstName, String lastName) {
        this.token = token;
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}