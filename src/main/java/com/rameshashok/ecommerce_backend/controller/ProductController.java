package com.rameshashok.ecommerce_backend.controller;

import com.rameshashok.ecommerce_backend.dto.ProductRequest;
import com.rameshashok.ecommerce_backend.dto.ProductResponse;
import com.rameshashok.ecommerce_backend.entity.Category;
import com.rameshashok.ecommerce_backend.entity.Product;
import com.rameshashok.ecommerce_backend.service.CategoryService;
import com.rameshashok.ecommerce_backend.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
@RequiredArgsConstructor
public class ProductController {
    
    private final ProductService productService;
    private final CategoryService categoryService;
    
    /**
     * Retrieves all products from the catalog.
     * 
     * @return List of all products
     */
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts(Pageable pageable) {
        if (pageable.isPaged()) {
            Page<ProductResponse> products = productService.getAllProducts(pageable);
            return ResponseEntity.ok(products.getContent());
        }
        return ResponseEntity.ok(productService.getAllProducts());
    }
    
    /**
     * Retrieves a specific product by its ID.
     * 
     * @param id the product ID
     * @return ResponseEntity containing the product if found, or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        ProductResponse product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }
    
    /**
     * Retrieves all products belonging to a specific category.
     * 
     * @param categoryId the category ID
     * @return List of products in the specified category
     */
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductResponse>> getProductsByCategory(@PathVariable Long categoryId) {
        List<ProductResponse> products = productService.getProductsByCategory(categoryId);
        return ResponseEntity.ok(products);
    }
    
    /**
     * Searches for products by name (case-insensitive partial match).
     * 
     * @param name the search term for product name
     * @return List of products matching the search criteria
     */
    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>> searchProducts(@RequestParam String name) {
        List<ProductResponse> products = productService.searchProductsByName(name);
        return ResponseEntity.ok(products);
    }
    
    /**
     * Creates a new product (Admin only).
     * 
     * @param product the product data to create
     * @return the created product
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request) {
        Category category = categoryService.getCategoryById(request.getCategoryId());
        
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setImageUrl(request.getImageUrl());
        product.setCategory(category);
        
        Product createdProduct = productService.createProduct(product);
        ProductResponse response = new ProductResponse(
                createdProduct.getId(),
                createdProduct.getName(),
                createdProduct.getDescription(),
                createdProduct.getPrice(),
                createdProduct.getStockQuantity(),
                createdProduct.getImageUrl(),
                createdProduct.getCategory().getId(),
                createdProduct.getCategory().getName(),
                createdProduct.getCreatedAt(),
                createdProduct.getUpdatedAt()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
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
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequest request) {
        Category category = categoryService.getCategoryById(request.getCategoryId());
        
        Product productDetails = new Product();
        productDetails.setName(request.getName());
        productDetails.setDescription(request.getDescription());
        productDetails.setPrice(request.getPrice());
        productDetails.setStockQuantity(request.getStockQuantity());
        productDetails.setImageUrl(request.getImageUrl());
        productDetails.setCategory(category);
        
        Product updatedProduct = productService.updateProduct(id, productDetails);
        ProductResponse response = new ProductResponse(
                updatedProduct.getId(),
                updatedProduct.getName(),
                updatedProduct.getDescription(),
                updatedProduct.getPrice(),
                updatedProduct.getStockQuantity(),
                updatedProduct.getImageUrl(),
                updatedProduct.getCategory().getId(),
                updatedProduct.getCategory().getName(),
                updatedProduct.getCreatedAt(),
                updatedProduct.getUpdatedAt()
        );
        return ResponseEntity.ok(response);
    }
    
    /**
     * Deletes a product (Admin only).
     * 
     * @param id the product ID to delete
     * @return ResponseEntity with 200 if deleted successfully, or 404 if not found
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}