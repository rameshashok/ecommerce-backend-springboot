package com.rameshashok.ecommerce_backend.service;

import com.rameshashok.ecommerce_backend.entity.Order;
import com.rameshashok.ecommerce_backend.entity.User;
import com.rameshashok.ecommerce_backend.exception.ResourceNotFoundException;
import com.rameshashok.ecommerce_backend.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service class for order management operations.
 * Handles business logic for order processing and tracking.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;

    /**
     * Retrieves all orders (admin only).
     * 
     * @return list of all orders
     */
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    /**
     * Retrieves orders for a specific user.
     * 
     * @param user the user
     * @return list of user's orders ordered by date descending
     */
    public List<Order> getOrdersByUser(User user) {
        return orderRepository.findByUserOrderByCreatedAtDesc(user);
    }

    /**
     * Retrieves an order by its ID.
     * 
     * @param id the order ID
     * @return the order
     * @throws ResourceNotFoundException if order not found
     */
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
    }

    /**
     * Retrieves an order by ID for a specific user.
     * 
     * @param id the order ID
     * @param user the user
     * @return the order
     * @throws ResourceNotFoundException if order not found or doesn't belong to user
     */
    public Order getOrderByIdAndUser(Long id, User user) {
        return orderRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
    }

    /**
     * Creates a new order.
     * 
     * @param order the order to create
     * @return the created order
     */
    @Transactional
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    /**
     * Updates the status of an order.
     * 
     * @param id the order ID
     * @param status the new order status
     * @return the updated order
     * @throws ResourceNotFoundException if order not found
     */
    @Transactional
    public Order updateOrderStatus(Long id, Order.OrderStatus status) {
        Order order = getOrderById(id);
        order.setStatus(status);
        return orderRepository.save(order);
    }
}