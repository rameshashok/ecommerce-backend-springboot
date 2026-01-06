package com.rameshashok.ecommerce_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

/**
 * Entity representing a product category in the ecommerce system.
 * Categories are used to organize and group related products.
 */
@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    /** Unique identifier for the category */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /** Category name (must be unique) */
    @Column(nullable = false, unique = true)
    private String name;
    
    /** Optional description of the category */
    private String description;
    
    /** List of products belonging to this category */
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Product> products;
}