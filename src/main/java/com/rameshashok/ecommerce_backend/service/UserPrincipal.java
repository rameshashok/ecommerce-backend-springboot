package com.rameshashok.ecommerce_backend.service;

import com.rameshashok.ecommerce_backend.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Collections;

/**
 * Implementation of Spring Security's UserDetails interface.
 * Represents an authenticated user with their authorities and account status.
 */
public class UserPrincipal implements UserDetails {
    /** User's unique identifier */
    private Long id;
    /** User's email address (used as username) */
    private String email;
    /** User's encrypted password */
    private String password;
    /** User's granted authorities/roles */
    private Collection<? extends GrantedAuthority> authorities;
    
    /**
     * Constructor for UserPrincipal.
     * 
     * @param id the user's ID
     * @param email the user's email
     * @param password the user's encrypted password
     * @param authorities the user's granted authorities
     */
    public UserPrincipal(Long id, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }
    
    /**
     * Factory method to create UserPrincipal from User entity.
     * 
     * @param user the User entity
     * @return UserPrincipal instance
     */
    public static UserPrincipal create(User user) {
        Collection<GrantedAuthority> authorities = Collections.singletonList(
            new SimpleGrantedAuthority("ROLE_" + user.getRole().name())
        );
        
        return new UserPrincipal(
            user.getId(),
            user.getEmail(),
            user.getPassword(),
            authorities
        );
    }
    
    /**
     * Gets the user's ID.
     * 
     * @return the user ID
     */
    public Long getId() {
        return id;
    }
    
    @Override
    public String getUsername() {
        return email;
    }
    
    @Override
    public String getPassword() {
        return password;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    
    @Override
    public boolean isEnabled() {
        return true;
    }
}