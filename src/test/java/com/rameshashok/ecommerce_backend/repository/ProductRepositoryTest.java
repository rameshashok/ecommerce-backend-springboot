package com.rameshashok.ecommerce_backend.repository;

import com.rameshashok.ecommerce_backend.entity.Category;
import com.rameshashok.ecommerce_backend.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void findByCategoryId_ExistingCategory_ReturnsProducts() {
        Category category = new Category();
        category.setName("Electronics");
        category.setDescription("Electronic devices");
        entityManager.persistAndFlush(category);

        Product product1 = new Product();
        product1.setName("Laptop");
        product1.setPrice(BigDecimal.valueOf(999.99));
        product1.setStockQuantity(10);
        product1.setCategory(category);

        Product product2 = new Product();
        product2.setName("Phone");
        product2.setPrice(BigDecimal.valueOf(599.99));
        product2.setStockQuantity(20);
        product2.setCategory(category);

        entityManager.persistAndFlush(product1);
        entityManager.persistAndFlush(product2);

        List<Product> products = productRepository.findByCategoryId(category.getId());

        assertEquals(2, products.size());
        assertTrue(products.stream().anyMatch(p -> p.getName().equals("Laptop")));
        assertTrue(products.stream().anyMatch(p -> p.getName().equals("Phone")));
    }

    @Test
    void findByNameContainingIgnoreCase_PartialMatch_ReturnsProducts() {
        Product product1 = new Product();
        product1.setName("Gaming Laptop");
        product1.setPrice(BigDecimal.valueOf(1299.99));
        product1.setStockQuantity(5);

        Product product2 = new Product();
        product2.setName("Office Laptop");
        product2.setPrice(BigDecimal.valueOf(799.99));
        product2.setStockQuantity(15);

        Product product3 = new Product();
        product3.setName("Gaming Mouse");
        product3.setPrice(BigDecimal.valueOf(49.99));
        product3.setStockQuantity(50);

        entityManager.persistAndFlush(product1);
        entityManager.persistAndFlush(product2);
        entityManager.persistAndFlush(product3);

        List<Product> laptops = productRepository.findByNameContainingIgnoreCase("laptop");
        List<Product> gaming = productRepository.findByNameContainingIgnoreCase("GAMING");

        assertEquals(2, laptops.size());
        assertEquals(2, gaming.size());
        assertTrue(laptops.stream().allMatch(p -> p.getName().toLowerCase().contains("laptop")));
    }
}