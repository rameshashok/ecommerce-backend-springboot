package com.rameshashok.ecommerce_backend.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class EntityTest {

    @Test
    void category_ShouldSetAndGetProperties() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Electronics");
        category.setDescription("Electronic devices");

        assertEquals(1L, category.getId());
        assertEquals("Electronics", category.getName());
        assertEquals("Electronic devices", category.getDescription());
    }

    @Test
    void product_ShouldSetAndGetProperties() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Smartphone");
        product.setPrice(new BigDecimal("699.99"));
        product.setStockQuantity(50);

        assertEquals(1L, product.getId());
        assertEquals("Smartphone", product.getName());
        assertEquals(new BigDecimal("699.99"), product.getPrice());
        assertEquals(50, product.getStockQuantity());
    }

    @Test
    void user_ShouldSetAndGetProperties() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setFirstName("John");
        user.setLastName("Doe");

        assertEquals(1L, user.getId());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
    }

    @Test
    void order_ShouldSetAndGetProperties() {
        Order order = new Order();
        order.setId(1L);
        order.setTotalAmount(new BigDecimal("100.00"));

        assertEquals(1L, order.getId());
        assertEquals(new BigDecimal("100.00"), order.getTotalAmount());
    }

    @Test
    void orderItem_ShouldSetAndGetProperties() {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(1L);
        orderItem.setQuantity(2);
        orderItem.setPrice(new BigDecimal("50.00"));

        assertEquals(1L, orderItem.getId());
        assertEquals(2, orderItem.getQuantity());
        assertEquals(new BigDecimal("50.00"), orderItem.getPrice());
    }
}