package com.rameshashok.ecommerce_backend.controller;

import com.rameshashok.ecommerce_backend.entity.Order;
import com.rameshashok.ecommerce_backend.entity.User;
import com.rameshashok.ecommerce_backend.service.OrderService;
import com.rameshashok.ecommerce_backend.service.UserPrincipal;
import com.rameshashok.ecommerce_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * REST controller for order management operations.
 * Provides endpoints for creating, retrieving, and updating orders with role-based access control.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    
    private final OrderService orderService;
    private final UserService userService;
    
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
        User user = userService.getUserById(userPrincipal.getId());
        return orderService.getOrdersByUser(user);
    }
    
    /**
     * Retrieves all orders in the system (Admin only).
     * 
     * @return List of all orders
     */
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
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
        Order order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }
    
    /**
     * Creates a new order with automatic inventory management.
     * 
     * @param order the order data to create
     * @param authentication the authentication context
     * @return the created order
     */
    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Order createOrder(@RequestBody Order order, Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        User user = userService.getUserById(userPrincipal.getId());
        order.setUser(user);
        return orderService.createOrder(order);
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
        Order order = orderService.updateOrderStatus(id, orderDetails.getStatus());
        return ResponseEntity.ok(order);
    }
}