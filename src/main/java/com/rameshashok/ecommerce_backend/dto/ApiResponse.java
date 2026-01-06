package com.rameshashok.ecommerce_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generic API response wrapper for consistent response format.
 * 
 * @param <T> the type of data being returned
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    
    /** Indicates if the operation was successful */
    private boolean success;
    
    /** Response message */
    private String message;
    
    /** Response data payload */
    private T data;
    
    /**
     * Creates a successful response with data.
     * 
     * @param data the response data
     * @param <T> the type of data
     * @return successful API response
     */
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, "Success", data);
    }
    
    /**
     * Creates a successful response with custom message and data.
     * 
     * @param message the success message
     * @param data the response data
     * @param <T> the type of data
     * @return successful API response
     */
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data);
    }
    
    /**
     * Creates an error response with message.
     * 
     * @param message the error message
     * @param <T> the type of data
     * @return error API response
     */
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, message, null);
    }
}