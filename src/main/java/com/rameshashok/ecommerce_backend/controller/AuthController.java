package com.rameshashok.ecommerce_backend.controller;

import com.rameshashok.ecommerce_backend.dto.*;
import com.rameshashok.ecommerce_backend.entity.User;
import com.rameshashok.ecommerce_backend.exception.BusinessException;
import com.rameshashok.ecommerce_backend.repository.UserRepository;
import com.rameshashok.ecommerce_backend.config.JwtUtils;
import com.rameshashok.ecommerce_backend.service.UserPrincipal;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for user authentication operations.
 * Handles user registration and login with JWT token generation.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    AuthenticationManager authenticationManager;
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    PasswordEncoder encoder;
    
    @Autowired
    JwtUtils jwtUtils;
    
    /**
     * Authenticates a user and returns a JWT token.
     * 
     * @param loginRequest the login credentials containing email and password
     * @return ResponseEntity containing JWT token and user information
     */
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        
        UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new BusinessException("User not found"));
        
        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), 
                userDetails.getUsername(), user.getFirstName(), user.getLastName()));
    }
    
    /**
     * Registers a new user in the system.
     * 
     * @param signUpRequest the registration data containing user information
     * @return ResponseEntity with success message or error if email already exists
     */
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new BusinessException("Email is already in use!");
        }
        
        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(encoder.encode(signUpRequest.getPassword()));
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        
        userRepository.save(user);
        
        return ResponseEntity.ok("User registered successfully!");
    }
}