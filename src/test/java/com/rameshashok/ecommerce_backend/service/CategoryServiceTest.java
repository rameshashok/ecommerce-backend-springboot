package com.rameshashok.ecommerce_backend.service;

import com.rameshashok.ecommerce_backend.entity.Category;
import com.rameshashok.ecommerce_backend.exception.ResourceNotFoundException;
import com.rameshashok.ecommerce_backend.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    private Category category;

    @BeforeEach
    void setUp() {
        category = new Category();
        category.setId(1L);
        category.setName("Electronics");
        category.setDescription("Electronic devices");
    }

    @Test
    void getAllCategories_ShouldReturnAllCategories() {
        List<Category> categories = Arrays.asList(category);
        when(categoryRepository.findAll()).thenReturn(categories);

        List<Category> result = categoryService.getAllCategories();

        assertEquals(1, result.size());
        assertEquals(category.getName(), result.get(0).getName());
        verify(categoryRepository).findAll();
    }

    @Test
    void getCategoryById_WhenExists_ShouldReturnCategory() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        Category result = categoryService.getCategoryById(1L);

        assertEquals(category.getName(), result.getName());
        verify(categoryRepository).findById(1L);
    }

    @Test
    void getCategoryById_WhenNotExists_ShouldThrowException() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> categoryService.getCategoryById(1L));
        verify(categoryRepository).findById(1L);
    }

    @Test
    void createCategory_ShouldSaveAndReturnCategory() {
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        Category result = categoryService.createCategory(category);

        assertEquals(category.getName(), result.getName());
        verify(categoryRepository).save(category);
    }

    @Test
    void updateCategory_WhenExists_ShouldUpdateAndReturnCategory() {
        Category updatedDetails = new Category();
        updatedDetails.setName("Updated Electronics");
        updatedDetails.setDescription("Updated description");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        Category result = categoryService.updateCategory(1L, updatedDetails);

        assertEquals("Updated Electronics", category.getName());
        assertEquals("Updated description", category.getDescription());
        verify(categoryRepository).findById(1L);
        verify(categoryRepository).save(category);
    }

    @Test
    void updateCategory_WhenNotExists_ShouldThrowException() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> 
            categoryService.updateCategory(1L, new Category()));
        verify(categoryRepository).findById(1L);
        verify(categoryRepository, never()).save(any());
    }

    @Test
    void deleteCategory_WhenExists_ShouldDeleteCategory() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        categoryService.deleteCategory(1L);

        verify(categoryRepository).findById(1L);
        verify(categoryRepository).delete(category);
    }

    @Test
    void deleteCategory_WhenNotExists_ShouldThrowException() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> categoryService.deleteCategory(1L));
        verify(categoryRepository).findById(1L);
        verify(categoryRepository, never()).delete(any());
    }
}