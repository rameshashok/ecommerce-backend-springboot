package com.rameshashok.ecommerce_backend.repository;

import com.rameshashok.ecommerce_backend.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByEmail_ExistingUser_ReturnsUser() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setRole(User.Role.USER);

        entityManager.persistAndFlush(user);

        Optional<User> found = userRepository.findByEmail("test@example.com");

        assertTrue(found.isPresent());
        assertEquals("test@example.com", found.get().getEmail());
        assertEquals("John", found.get().getFirstName());
    }

    @Test
    void findByEmail_NonExistingUser_ReturnsEmpty() {
        Optional<User> found = userRepository.findByEmail("nonexistent@example.com");
        assertFalse(found.isPresent());
    }

    @Test
    void existsByEmail_ExistingUser_ReturnsTrue() {
        User user = new User();
        user.setEmail("existing@example.com");
        user.setPassword("password");
        user.setFirstName("Jane");
        user.setLastName("Doe");

        entityManager.persistAndFlush(user);

        boolean exists = userRepository.existsByEmail("existing@example.com");
        assertTrue(exists);
    }

    @Test
    void existsByEmail_NonExistingUser_ReturnsFalse() {
        boolean exists = userRepository.existsByEmail("nonexistent@example.com");
        assertFalse(exists);
    }
}