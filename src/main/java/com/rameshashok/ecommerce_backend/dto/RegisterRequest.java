package com.rameshashok.ecommerce_backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Data Transfer Object for user registration requests.
 * Contains all the information required to create a new user account.
 */
@Data
public class RegisterRequest {
    /** User's email address (must be valid email format) */
    @Email
    @NotBlank
    private String email;
    
    /** User's password (minimum 6 characters) */
    @NotBlank
    @Size(min = 6)
    private String password;
    
    /** User's first name (cannot be blank) */
    @NotBlank
    private String firstName;
    
    /** User's last name (cannot be blank) */
    @NotBlank
    private String lastName;
}