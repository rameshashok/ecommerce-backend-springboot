package com.rameshashok.ecommerce_backend.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;

/**
 * Utility class for JWT token operations.
 * Handles JWT token generation, validation, and extraction of user information.
 */
@Component
@RequiredArgsConstructor
public class JwtUtils {
    
    private final JwtProperties jwtProperties;
    
    /**
     * Creates the signing key for JWT tokens using HMAC-SHA algorithm.
     * 
     * @return the signing key
     */
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
    }
    
    /**
     * Generates a JWT token for an authenticated user.
     * 
     * @param authentication the authentication object containing user details
     * @return the generated JWT token
     */
    public String generateJwtToken(Authentication authentication) {
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtProperties.getExpiration()))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    
    /**
     * Extracts the username from a JWT token.
     * 
     * @param token the JWT token
     * @return the username contained in the token
     */
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    
    /**
     * Validates a JWT token for authenticity and expiration.
     * 
     * @param authToken the JWT token to validate
     * @return true if the token is valid, false otherwise
     */
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(authToken);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}