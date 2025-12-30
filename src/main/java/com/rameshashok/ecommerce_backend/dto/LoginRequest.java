package com.rameshashok.ecommerce_backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Data Transfer Object for user login requests.
 * Contains the credentials required for user authentication.
 */
@Data
public class LoginRequest {
    /** User's email address (must be valid email format) */
    @Email
    @NotBlank
    private String email;
    
    /** User's password (cannot be blank) */
    @NotBlank
    private String password;
}