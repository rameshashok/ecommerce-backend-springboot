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

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryController.class)
@Import(com.rameshashok.ecommerce_backend.config.TestSecurityConfig.class)
class CategoryControllerBusinessLogicTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryRepository categoryRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateCategory_ShouldUpdateExistingCategory() throws Exception {
        Category existingCategory = new Category();
        existingCategory.setId(1L);
        existingCategory.setName("Old Name");
        existingCategory.setDescription("Old Description");

        Category updatedCategory = new Category();
        updatedCategory.setName("New Name");
        updatedCategory.setDescription("New Description");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.save(any(Category.class))).thenReturn(existingCategory);

        String categoryJson = objectMapper.writeValueAsString(updatedCategory);

        mockMvc.perform(put("/api/categories/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(categoryJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New Name"))
                .andExpect(jsonPath("$.description").value("New Description"));
    }

    @Test
    void getCategoryById_NotFound_ShouldReturn404() throws Exception {
        when(categoryRepository.findById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/categories/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateCategory_NotFound_ShouldReturn404() throws Exception {
        Category updatedCategory = new Category();
        updatedCategory.setName("New Name");

        when(categoryRepository.findById(999L)).thenReturn(Optional.empty());

        String categoryJson = objectMapper.writeValueAsString(updatedCategory);

        mockMvc.perform(put("/api/categories/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(categoryJson))
                .andExpect(status().isNotFound());
    }
}