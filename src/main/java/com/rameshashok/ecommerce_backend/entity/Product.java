package com.rameshashok.ecommerce_backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entity representing a product in the ecommerce catalog.
 * Contains product information including pricing, inventory, and category association.
 */
@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    /** Unique identifier for the product */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /** Product name */
    @Column(nullable = false)
    private String name;
    
    /** Detailed description of the product */
    @Column(length = 1000)
    private String description;
    
    /** Product price with precision for currency calculations */
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
    
    /** Available quantity in stock */
    @Column(nullable = false)
    private Integer stockQuantity;
    
    /** URL to the product image */
    private String imageUrl;
    
    /** Category this product belongs to */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
    
    /** Timestamp when the product was created */
    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    
    /** Timestamp when the product was last updated */
    private LocalDateTime updatedAt = LocalDateTime.now();
    
    /**
     * JPA callback method to update the updatedAt timestamp before entity update.
     */
    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}