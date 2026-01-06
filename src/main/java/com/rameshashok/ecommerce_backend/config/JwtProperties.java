package com.rameshashok.ecommerce_backend.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Configuration properties for JWT token settings.
 * Maps JWT-related properties from application configuration.
 */
@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    
    /** JWT secret key for token signing */
    private String secret;
    
    /** JWT token expiration time in milliseconds */
    private long expiration;
}