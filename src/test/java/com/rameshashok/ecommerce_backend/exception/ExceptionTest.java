package com.rameshashok.ecommerce_backend.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionTest {

    @Test
    void resourceNotFoundException_ShouldCreateWithMessage() {
        String message = "Resource not found";
        ResourceNotFoundException exception = new ResourceNotFoundException(message);
        
        assertEquals(message, exception.getMessage());
    }

    @Test
    void businessException_ShouldCreateWithMessage() {
        String message = "Business error occurred";
        BusinessException exception = new BusinessException(message);
        
        assertEquals(message, exception.getMessage());
    }
}