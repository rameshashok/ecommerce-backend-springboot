package com.rameshashok.ecommerce_backend.controller;

import com.rameshashok.ecommerce_backend.entity.Order;
import com.rameshashok.ecommerce_backend.repository.OrderRepository;
import com.rameshashok.ecommerce_backend.service.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for order management operations.
 * Provides endpoints for creating, retrieving, and updating orders with role-based access control.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    
    @Autowired
    private OrderRepository orderRepository;
    
    /**
     * Retrieves all orders for the authenticated user.
     * 
     * @param authentication the authentication context containing user information
     * @return List of orders belonging to the authenticated user
     */
    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<Order> getUserOrders(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return orderRepository.findByUserId(userPrincipal.getId());
    }
    
    /**
     * Retrieves all orders in the system (Admin only).
     * 
     * @return List of all orders
     */
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
    
    /**
     * Retrieves a specific order by its ID.
     * 
     * @param id the order ID
     * @return ResponseEntity containing the order if found, or 404 if not found
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Optional<Order> order = orderRepository.findById(id);
        return order.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Creates a new order.
     * 
     * @param order the order data to create
     * @return the created order
     */
    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Order createOrder(@RequestBody Order order) {
        return orderRepository.save(order);
    }
    
    /**
     * Updates the status of an existing order (Admin only).
     * 
     * @param id the order ID to update
     * @param orderDetails the order data containing the new status
     * @return ResponseEntity containing the updated order or 404 if not found
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long id, @RequestBody Order orderDetails) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setStatus(orderDetails.getStatus());
            return ResponseEntity.ok(orderRepository.save(order));
        }
        return ResponseEntity.notFound().build();
    }
}