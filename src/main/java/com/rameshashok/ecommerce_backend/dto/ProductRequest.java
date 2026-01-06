package com.rameshashok.ecommerce_backend.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Data Transfer Object for product creation and update requests.
 * Contains validation constraints for product data.
 */
@Data
public class ProductRequest {
    
    /** Product name - required field */
    @NotBlank(message = "Product name is required")
    private String name;
    
    /** Product description - optional field */
    private String description;
    
    /** Product price - must be greater than 0 */
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal price;
    
    /** Stock quantity - cannot be negative */
    @NotNull(message = "Stock quantity is required")
    @Min(value = 0, message = "Stock quantity cannot be negative")
    private Integer stockQuantity;
    
    /** Product image URL - optional field */
    private String imageUrl;
    
    /** Category ID - required field */
    @NotNull(message = "Category ID is required")
    private Long categoryId;
}