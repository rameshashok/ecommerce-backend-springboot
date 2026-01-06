package com.rameshashok.ecommerce_backend.service;

import com.rameshashok.ecommerce_backend.dto.ProductResponse;
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
     * Retrieves all products as DTOs.
     * 
     * @return list of all products as DTOs
     */
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::convertToResponse)
                .toList();
    }

    /**
     * Retrieves products with pagination as DTOs.
     * 
     * @param pageable pagination information
     * @return page of products as DTOs
     */
    public Page<ProductResponse> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(this::convertToResponse);
    }

    /**
     * Retrieves a product by its ID as DTO.
     * 
     * @param id the product ID
     * @return the product as DTO
     * @throws ResourceNotFoundException if product not found
     */
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return convertToResponse(product);
    }

    /**
     * Retrieves products by category ID as DTOs.
     * 
     * @param categoryId the category ID
     * @return list of products in the category as DTOs
     */
    public List<ProductResponse> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId).stream()
                .map(this::convertToResponse)
                .toList();
    }

    /**
     * Searches products by name (case-insensitive) as DTOs.
     * 
     * @param name the search term
     * @return list of matching products as DTOs
     */
    public List<ProductResponse> searchProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name).stream()
                .map(this::convertToResponse)
                .toList();
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
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
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
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        productRepository.delete(product);
    }

    /**
     * Converts Product entity to ProductResponse DTO.
     * 
     * @param product the product entity
     * @return the product response DTO
     */
    private ProductResponse convertToResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStockQuantity(),
                product.getImageUrl(),
                product.getCategory() != null ? product.getCategory().getId() : null,
                product.getCategory() != null ? product.getCategory().getName() : null,
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }
}