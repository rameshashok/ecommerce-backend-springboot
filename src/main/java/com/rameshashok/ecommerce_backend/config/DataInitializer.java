package com.rameshashok.ecommerce_backend.config;

import com.rameshashok.ecommerce_backend.entity.Category;
import com.rameshashok.ecommerce_backend.entity.Product;
import com.rameshashok.ecommerce_backend.repository.CategoryRepository;
import com.rameshashok.ecommerce_backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Override
    public void run(String... args) {
        if (categoryRepository.count() == 0) {
            initializeData();
        }
    }

    private void initializeData() {
        // Create categories
        Category electronics = createCategory("Electronics", "Electronic devices and gadgets");
        Category clothing = createCategory("Clothing", "Fashion and apparel");
        Category books = createCategory("Books", "Books and literature");
        Category home = createCategory("Home & Garden", "Home improvement and garden supplies");

        // Create products
        createProduct("iPhone 15", "Latest Apple smartphone with advanced features", 
                     new BigDecimal("999.99"), 25, "https://example.com/iphone15.jpg", electronics);
        
        createProduct("Samsung Galaxy S24", "Premium Android smartphone", 
                     new BigDecimal("899.99"), 30, "https://example.com/galaxy-s24.jpg", electronics);
        
        createProduct("MacBook Pro", "Professional laptop for developers", 
                     new BigDecimal("1999.99"), 15, "https://example.com/macbook.jpg", electronics);
        
        createProduct("Wireless Headphones", "Noise-cancelling Bluetooth headphones", 
                     new BigDecimal("199.99"), 50, "https://example.com/headphones.jpg", electronics);

        createProduct("Cotton T-Shirt", "Comfortable cotton t-shirt", 
                     new BigDecimal("29.99"), 100, "https://example.com/tshirt.jpg", clothing);
        
        createProduct("Jeans", "Classic blue denim jeans", 
                     new BigDecimal("79.99"), 75, "https://example.com/jeans.jpg", clothing);
        
        createProduct("Sneakers", "Comfortable running sneakers", 
                     new BigDecimal("129.99"), 40, "https://example.com/sneakers.jpg", clothing);

        createProduct("Java Programming Book", "Complete guide to Java programming", 
                     new BigDecimal("49.99"), 60, "https://example.com/java-book.jpg", books);
        
        createProduct("Spring Boot in Action", "Learn Spring Boot framework", 
                     new BigDecimal("59.99"), 35, "https://example.com/spring-book.jpg", books);

        createProduct("Coffee Maker", "Automatic drip coffee maker", 
                     new BigDecimal("89.99"), 20, "https://example.com/coffee-maker.jpg", home);
        
        createProduct("Garden Tools Set", "Complete set of gardening tools", 
                     new BigDecimal("149.99"), 25, "https://example.com/garden-tools.jpg", home);
    }

    private Category createCategory(String name, String description) {
        Category category = new Category();
        category.setName(name);
        category.setDescription(description);
        return categoryRepository.save(category);
    }

    private Product createProduct(String name, String description, BigDecimal price, 
                                 Integer stockQuantity, String imageUrl, Category category) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setStockQuantity(stockQuantity);
        product.setImageUrl(imageUrl);
        product.setCategory(category);
        return productRepository.save(product);
    }
}