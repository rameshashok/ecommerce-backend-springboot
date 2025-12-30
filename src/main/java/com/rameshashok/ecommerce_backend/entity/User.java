package com.rameshashok.ecommerce_backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Entity representing a user in the ecommerce system.
 * Contains user authentication information and profile data.
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    /** Unique identifier for the user */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /** User's email address, used for authentication */
    @Column(unique = true, nullable = false)
    private String email;
    
    /** Encrypted password for user authentication */
    @Column(nullable = false)
    private String password;
    
    /** User's first name */
    @Column(nullable = false)
    private String firstName;
    
    /** User's last name */
    @Column(nullable = false)
    private String lastName;
    
    /** User's role in the system (USER or ADMIN) */
    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;
    
    /** Timestamp when the user account was created */
    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    
    /** List of orders placed by this user */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> orders;
    
    /**
     * Enumeration of user roles in the system.
     */
    public enum Role {
        /** Regular user with basic permissions */
        USER, 
        /** Administrator with elevated permissions */
        ADMIN
    }
}