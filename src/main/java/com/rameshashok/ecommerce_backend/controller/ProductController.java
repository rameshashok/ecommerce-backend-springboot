package com.rameshashok.ecommerce_backend.controller;

import com.rameshashok.ecommerce_backend.entity.Product;
import com.rameshashok.ecommerce_backend.exception.ResourceNotFoundException;
import com.rameshashok.ecommerce_backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * REST controller for product management operations.
 * Provides endpoints for CRUD operations on products with role-based access control.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/products")
public class ProductController {
    
    @Autowired
    private ProductRepository productRepository;
    
    /**
     * Retrieves all products from the catalog.
     * 
     * @return List of all products
     */
    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    
    /**
     * Retrieves a specific product by its ID.
     * 
     * @param id the product ID
     * @return ResponseEntity containing the product if found, or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return ResponseEntity.ok(product);
    }
    
    /**
     * Retrieves all products belonging to a specific category.
     * 
     * @param categoryId the category ID
     * @return List of products in the specified category
     */
    @GetMapping("/category/{categoryId}")
    public List<Product> getProductsByCategory(@PathVariable Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }
    
    /**
     * Searches for products by name (case-insensitive partial match).
     * 
     * @param name the search term for product name
     * @return List of products matching the search criteria
     */
    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }
    
    /**
     * Creates a new product (Admin only).
     * 
     * @param product the product data to create
     * @return the created product
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Product createProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }
    
    /**
     * Updates an existing product (Admin only).
     * 
     * @param id the product ID to update
     * @param productDetails the updated product data
     * @return ResponseEntity containing the updated product or 404 if not found
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        
        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        product.setStockQuantity(productDetails.getStockQuantity());
        product.setImageUrl(productDetails.getImageUrl());
        product.setCategory(productDetails.getCategory());
        
        return ResponseEntity.ok(productRepository.save(product));
    }
    
    /**
     * Deletes a product (Admin only).
     * 
     * @param id the product ID to delete
     * @return ResponseEntity with 200 if deleted successfully, or 404 if not found
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}