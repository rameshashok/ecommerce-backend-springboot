package com.rameshashok.ecommerce_backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Entity representing a customer order in the ecommerce system.
 * Contains order information, status tracking, and associated order items.
 */
@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    /** Unique identifier for the order */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /** User who placed this order */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    /** Total amount for the entire order */
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;
    
    /** Current status of the order */
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.PENDING;
    
    /** Timestamp when the order was created */
    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    
    /** List of items included in this order */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;
    
    /**
     * Enumeration of possible order statuses.
     */
    public enum OrderStatus {
        /** Order has been placed but not yet confirmed */
        PENDING, 
        /** Order has been confirmed and is being processed */
        CONFIRMED, 
        /** Order has been shipped to the customer */
        SHIPPED, 
        /** Order has been delivered to the customer */
        DELIVERED, 
        /** Order has been cancelled */
        CANCELLED
    }
}