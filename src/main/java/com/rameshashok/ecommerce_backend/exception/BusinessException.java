package com.rameshashok.ecommerce_backend.exception;

/**
 * Exception thrown when business rules are violated.
 */
public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}