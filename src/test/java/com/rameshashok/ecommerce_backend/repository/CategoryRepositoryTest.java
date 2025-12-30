package com.rameshashok.ecommerce_backend.repository;

import com.rameshashok.ecommerce_backend.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CategoryRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void shouldSaveAndFindCategory() {
        Category category = new Category();
        category.setName("Electronics");
        category.setDescription("Electronic devices");
        
        Category saved = categoryRepository.save(category);
        
        assertNotNull(saved.getId());
        assertEquals("Electronics", saved.getName());
        assertEquals("Electronic devices", saved.getDescription());
    }

    @Test
    void shouldFindAllCategories() {
        Category category1 = new Category();
        category1.setName("Electronics");
        entityManager.persistAndFlush(category1);

        Category category2 = new Category();
        category2.setName("Books");
        entityManager.persistAndFlush(category2);

        assertEquals(2, categoryRepository.findAll().size());
    }
}