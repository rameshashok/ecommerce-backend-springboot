package com.rameshashok.ecommerce_backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

/**
 * Entity representing an individual item within an order.
 * Links a specific product to an order with quantity and price information.
 */
@Entity
@Table(name = "order_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    /** Unique identifier for the order item */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /** The order this item belongs to */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
    
    /** The product being ordered */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    
    /** Quantity of the product being ordered */
    @Column(nullable = false)
    private Integer quantity;
    
    /** Price per unit at the time of order (may differ from current product price) */
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
}