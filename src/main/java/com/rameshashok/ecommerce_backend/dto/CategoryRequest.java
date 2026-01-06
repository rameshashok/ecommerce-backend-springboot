package com.rameshashok.ecommerce_backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Data Transfer Object for category creation and update requests.
 * Contains validation constraints for category data.
 */
@Data
public class CategoryRequest {
    
    /** Category name - required field */
    @NotBlank(message = "Category name is required")
    private String name;
    
    /** Category description - optional field */
    private String description;
}