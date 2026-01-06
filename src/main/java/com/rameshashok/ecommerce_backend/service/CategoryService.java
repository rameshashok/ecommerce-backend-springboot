package com.rameshashok.ecommerce_backend.service;

import com.rameshashok.ecommerce_backend.entity.Category;
import com.rameshashok.ecommerce_backend.exception.ResourceNotFoundException;
import com.rameshashok.ecommerce_backend.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service class for category management operations.
 * Handles business logic for category CRUD operations.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    /**
     * Retrieves all categories.
     * 
     * @return list of all categories
     */
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    /**
     * Retrieves a category by its ID.
     * 
     * @param id the category ID
     * @return the category
     * @throws ResourceNotFoundException if category not found
     */
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
    }

    /**
     * Creates a new category.
     * 
     * @param category the category to create
     * @return the created category
     */
    @Transactional
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    /**
     * Updates an existing category.
     * 
     * @param id the category ID
     * @param categoryDetails the updated category data
     * @return the updated category
     * @throws ResourceNotFoundException if category not found
     */
    @Transactional
    public Category updateCategory(Long id, Category categoryDetails) {
        Category category = getCategoryById(id);
        category.setName(categoryDetails.getName());
        category.setDescription(categoryDetails.getDescription());
        return categoryRepository.save(category);
    }

    /**
     * Deletes a category by ID.
     * 
     * @param id the category ID
     * @throws ResourceNotFoundException if category not found
     */
    @Transactional
    public void deleteCategory(Long id) {
        Category category = getCategoryById(id);
        categoryRepository.delete(category);
    }
}