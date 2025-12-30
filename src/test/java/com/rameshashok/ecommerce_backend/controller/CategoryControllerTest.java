package com.rameshashok.ecommerce_backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rameshashok.ecommerce_backend.entity.Category;
import com.rameshashok.ecommerce_backend.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryController.class)
@Import(com.rameshashok.ecommerce_backend.config.TestSecurityConfig.class)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryRepository categoryRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllCategories_ShouldReturnCategories() throws Exception {
        Category category = new Category();
        category.setId(1L);
        category.setName("Electronics");

        when(categoryRepository.findAll()).thenReturn(Arrays.asList(category));

        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void getCategoryById_ShouldReturnCategory() throws Exception {
        Category category = new Category();
        category.setId(1L);
        category.setName("Electronics");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        mockMvc.perform(get("/api/categories/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Electronics"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createCategory_ShouldReturnCreatedCategory() throws Exception {
        Category category = new Category();
        category.setName("Electronics");

        Category savedCategory = new Category();
        savedCategory.setId(1L);
        savedCategory.setName("Electronics");

        when(categoryRepository.save(any(Category.class))).thenReturn(savedCategory);

        mockMvc.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(category)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteCategory_ShouldReturnNoContent() throws Exception {
        Category category = new Category();
        category.setId(1L);

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        mockMvc.perform(delete("/api/categories/1"))
                .andExpect(status().isNotFound());
    }
}