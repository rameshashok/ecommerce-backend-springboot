package com.rameshashok.ecommerce_backend.config;

import com.rameshashok.ecommerce_backend.service.UserPrincipal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtUtilsTest {

    @Mock
    private JwtProperties jwtProperties;

    @InjectMocks
    private JwtUtils jwtUtils;

    @BeforeEach
    void setUp() {
        when(jwtProperties.getSecret()).thenReturn("testSecretKeyForJwtTokenGenerationAndValidation");
        when(jwtProperties.getExpiration()).thenReturn(86400000L);
    }

    @Test
    void generateJwtToken_ValidAuthentication_ReturnsToken() {
        UserPrincipal userPrincipal = new UserPrincipal(1L, "test@example.com", "password",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        Authentication authentication = new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());

        String token = jwtUtils.generateJwtToken(authentication);

        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void getUserNameFromJwtToken_ValidToken_ReturnsUsername() {
        UserPrincipal userPrincipal = new UserPrincipal(1L, "test@example.com", "password",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        Authentication authentication = new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());

        String token = jwtUtils.generateJwtToken(authentication);
        String username = jwtUtils.getUserNameFromJwtToken(token);

        assertEquals("test@example.com", username);
    }

    @Test
    void validateJwtToken_ValidToken_ReturnsTrue() {
        UserPrincipal userPrincipal = new UserPrincipal(1L, "test@example.com", "password",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        Authentication authentication = new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());

        String token = jwtUtils.generateJwtToken(authentication);
        boolean isValid = jwtUtils.validateJwtToken(token);

        assertTrue(isValid);
    }

    @Test
    void validateJwtToken_InvalidToken_ReturnsFalse() {
        String invalidToken = "invalid.jwt.token";
        boolean isValid = jwtUtils.validateJwtToken(invalidToken);

        assertFalse(isValid);
    }

    @Test
    void validateJwtToken_EmptyToken_ReturnsFalse() {
        boolean isValid = jwtUtils.validateJwtToken("");

        assertFalse(isValid);
    }
}