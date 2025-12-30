package com.rameshashok.ecommerce_backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rameshashok.ecommerce_backend.entity.Category;
import com.rameshashok.ecommerce_backend.entity.Product;
import com.rameshashok.ecommerce_backend.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
@Import(com.rameshashok.ecommerce_backend.config.TestSecurityConfig.class)
class ProductControllerBusinessLogicTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void searchProducts_ShouldReturnMatchingProducts() throws Exception {
        Product product = new Product();
        product.setId(1L);
        product.setName("Smartphone");
        product.setPrice(new BigDecimal("699.99"));

        when(productRepository.findByNameContainingIgnoreCase("phone")).thenReturn(Arrays.asList(product));

        mockMvc.perform(get("/api/products/search").param("name", "phone"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Smartphone"));
    }

    @Test
    void getProductsByCategory_ShouldReturnCategoryProducts() throws Exception {
        Category category = new Category();
        category.setId(1L);
        category.setName("Electronics");

        Product product = new Product();
        product.setId(1L);
        product.setName("Smartphone");
        product.setCategory(category);

        when(productRepository.findByCategoryId(1L)).thenReturn(Arrays.asList(product));

        mockMvc.perform(get("/api/products/category/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Smartphone"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateProduct_ShouldUpdateExistingProduct() throws Exception {
        Product existingProduct = new Product();
        existingProduct.setId(1L);
        existingProduct.setName("Old Name");
        existingProduct.setPrice(new BigDecimal("100.00"));

        Product updatedProduct = new Product();
        updatedProduct.setId(1L);
        updatedProduct.setName("New Name");
        updatedProduct.setPrice(new BigDecimal("150.00"));

        when(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

        String productJson = objectMapper.writeValueAsString(updatedProduct);

        mockMvc.perform(put("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New Name"))
                .andExpect(jsonPath("$.price").value(150.00));
    }

    @Test
    void getProductById_NotFound_ShouldReturn404() throws Exception {
        when(productRepository.findById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/products/999"))
                .andExpect(status().isNotFound());
    }
}