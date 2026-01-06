package com.rameshashok.ecommerce_backend.service;

import com.rameshashok.ecommerce_backend.entity.Product;
import com.rameshashok.ecommerce_backend.exception.ResourceNotFoundException;
import com.rameshashok.ecommerce_backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service class for product management operations.
 * Handles business logic for product CRUD operations.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    /**
     * Retrieves all products.
     * 
     * @return list of all products
     */
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Retrieves products with pagination.
     * 
     * @param pageable pagination information
     * @return page of products
     */
    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    /**
     * Retrieves a product by its ID.
     * 
     * @param id the product ID
     * @return the product
     * @throws ResourceNotFoundException if product not found
     */
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    /**
     * Retrieves products by category ID.
     * 
     * @param categoryId the category ID
     * @return list of products in the category
     */
    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    /**
     * Searches products by name (case-insensitive).
     * 
     * @param name the search term
     * @return list of matching products
     */
    public List<Product> searchProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    /**
     * Creates a new product.
     * 
     * @param product the product to create
     * @return the created product
     */
    @Transactional
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    /**
     * Updates an existing product.
     * 
     * @param id the product ID
     * @param productDetails the updated product data
     * @return the updated product
     * @throws ResourceNotFoundException if product not found
     */
    @Transactional
    public Product updateProduct(Long id, Product productDetails) {
        Product product = getProductById(id);
        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        product.setStockQuantity(productDetails.getStockQuantity());
        product.setImageUrl(productDetails.getImageUrl());
        product.setCategory(productDetails.getCategory());
        return productRepository.save(product);
    }

    /**
     * Deletes a product by ID.
     * 
     * @param id the product ID
     * @throws ResourceNotFoundException if product not found
     */
    @Transactional
    public void deleteProduct(Long id) {
        Product product = getProductById(id);
        productRepository.delete(product);
    }
}