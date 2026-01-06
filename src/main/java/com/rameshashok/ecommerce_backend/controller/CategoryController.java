package com.rameshashok.ecommerce_backend.controller;

import com.rameshashok.ecommerce_backend.dto.CategoryRequest;
import com.rameshashok.ecommerce_backend.entity.Category;
import com.rameshashok.ecommerce_backend.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * REST controller for category management operations.
 * Provides endpoints for CRUD operations on product categories with role-based access control.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    
    private final CategoryService categoryService;
    
    /**
     * Retrieves all categories.
     * 
     * @return List of all categories
     */
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }
    
    /**
     * Retrieves a specific category by its ID.
     * 
     * @param id the category ID
     * @return ResponseEntity containing the category if found, or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(category);
    }
    
    /**
     * Creates a new category (Admin only).
     * 
     * @param category the category data to create
     * @return the created category
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Category> createCategory(@Valid @RequestBody CategoryRequest request) {
        Category category = new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        
        Category createdCategory = categoryService.createCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }
    
    /**
     * Updates an existing category (Admin only).
     * 
     * @param id the category ID to update
     * @param categoryDetails the updated category data
     * @return ResponseEntity containing the updated category or 404 if not found
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryRequest request) {
        Category categoryDetails = new Category();
        categoryDetails.setName(request.getName());
        categoryDetails.setDescription(request.getDescription());
        
        Category updatedCategory = categoryService.updateCategory(id, categoryDetails);
        return ResponseEntity.ok(updatedCategory);
    }
    
    /**
     * Deletes a category (Admin only).
     * 
     * @param id the category ID to delete
     * @return ResponseEntity with 200 if deleted successfully, or 404 if not found
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}