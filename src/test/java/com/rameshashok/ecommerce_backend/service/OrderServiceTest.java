package com.rameshashok.ecommerce_backend.service;

import com.rameshashok.ecommerce_backend.entity.Order;
import com.rameshashok.ecommerce_backend.entity.User;
import com.rameshashok.ecommerce_backend.exception.ResourceNotFoundException;
import com.rameshashok.ecommerce_backend.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    private Order order;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setEmail("user@example.com");

        order = new Order();
        order.setId(1L);
        order.setUser(user);
        order.setTotalAmount(BigDecimal.valueOf(100.00));
        order.setStatus(Order.OrderStatus.PENDING);
        order.setCreatedAt(LocalDateTime.now());
    }

    @Test
    void getAllOrders_ShouldReturnAllOrders() {
        List<Order> orders = Arrays.asList(order);
        when(orderRepository.findAll()).thenReturn(orders);

        List<Order> result = orderService.getAllOrders();

        assertEquals(1, result.size());
        assertEquals(order.getId(), result.get(0).getId());
        verify(orderRepository).findAll();
    }

    @Test
    void getOrdersByUser_ShouldReturnUserOrders() {
        List<Order> orders = Arrays.asList(order);
        when(orderRepository.findByUserOrderByCreatedAtDesc(user)).thenReturn(orders);

        List<Order> result = orderService.getOrdersByUser(user);

        assertEquals(1, result.size());
        assertEquals(order.getId(), result.get(0).getId());
        verify(orderRepository).findByUserOrderByCreatedAtDesc(user);
    }

    @Test
    void getOrderById_WhenExists_ShouldReturnOrder() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        Order result = orderService.getOrderById(1L);

        assertEquals(order.getId(), result.getId());
        verify(orderRepository).findById(1L);
    }

    @Test
    void getOrderById_WhenNotExists_ShouldThrowException() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> orderService.getOrderById(1L));
        verify(orderRepository).findById(1L);
    }

    @Test
    void getOrderByIdAndUser_WhenExists_ShouldReturnOrder() {
        when(orderRepository.findByIdAndUser(1L, user)).thenReturn(Optional.of(order));

        Order result = orderService.getOrderByIdAndUser(1L, user);

        assertEquals(order.getId(), result.getId());
        verify(orderRepository).findByIdAndUser(1L, user);
    }

    @Test
    void getOrderByIdAndUser_WhenNotExists_ShouldThrowException() {
        when(orderRepository.findByIdAndUser(1L, user)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> 
            orderService.getOrderByIdAndUser(1L, user));
        verify(orderRepository).findByIdAndUser(1L, user);
    }

    @Test
    void createOrder_ShouldSaveAndReturnOrder() {
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order result = orderService.createOrder(order);

        assertEquals(order.getId(), result.getId());
        verify(orderRepository).save(order);
    }

    @Test
    void updateOrderStatus_WhenExists_ShouldUpdateAndReturnOrder() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order result = orderService.updateOrderStatus(1L, Order.OrderStatus.SHIPPED);

        assertEquals(Order.OrderStatus.SHIPPED, order.getStatus());
        verify(orderRepository).findById(1L);
        verify(orderRepository).save(order);
    }

    @Test
    void updateOrderStatus_WhenNotExists_ShouldThrowException() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> 
            orderService.updateOrderStatus(1L, Order.OrderStatus.SHIPPED));
        verify(orderRepository).findById(1L);
        verify(orderRepository, never()).save(any());
    }
}