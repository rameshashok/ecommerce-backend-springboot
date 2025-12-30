package com.rameshashok.ecommerce_backend.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationProvider;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WebSecurityConfigTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Test
    void passwordEncoder_ShouldBeConfigured() {
        assertNotNull(passwordEncoder);
        
        String rawPassword = "testPassword";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        
        assertNotNull(encodedPassword);
        assertNotEquals(rawPassword, encodedPassword);
        assertTrue(passwordEncoder.matches(rawPassword, encodedPassword));
    }

    @Test
    void authenticationProvider_ShouldBeConfigured() {
        assertNotNull(authenticationProvider);
    }
}