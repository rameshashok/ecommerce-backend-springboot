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
                     new BigDecimal("999.99"), 25, "https://images.unsplash.com/photo-1592750475338-74b7b21085ab?w=400", electronics);
        
        createProduct("Samsung Galaxy S24", "Premium Android smartphone", 
                     new BigDecimal("899.99"), 30, "https://images.unsplash.com/photo-1511707171634-5f897ff02aa9?w=400", electronics);
        
        createProduct("MacBook Pro", "Professional laptop for developers", 
                     new BigDecimal("1999.99"), 15, "https://images.unsplash.com/photo-1517336714731-489689fd1ca8?w=400", electronics);
        
        createProduct("Wireless Headphones", "Noise-cancelling Bluetooth headphones", 
                     new BigDecimal("199.99"), 50, "https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=400", electronics);

        createProduct("Cotton T-Shirt", "Comfortable cotton t-shirt", 
                     new BigDecimal("29.99"), 100, "https://images.unsplash.com/photo-1521572163474-6864f9cf17ab?w=400", clothing);
        
        createProduct("Jeans", "Classic blue denim jeans", 
                     new BigDecimal("79.99"), 75, "https://images.unsplash.com/photo-1542272604-787c3835535d?w=400", clothing);
        
        createProduct("Sneakers", "Comfortable running sneakers", 
                     new BigDecimal("129.99"), 40, "https://images.unsplash.com/photo-1549298916-b41d501d3772?w=400", clothing);

        createProduct("Java Programming Book", "Complete guide to Java programming", 
                     new BigDecimal("49.99"), 60, "https://images.unsplash.com/photo-1544716278-ca5e3f4abd8c?w=400", books);
        
        createProduct("Spring Boot in Action", "Learn Spring Boot framework", 
                     new BigDecimal("59.99"), 35, "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=400", books);

        createProduct("Coffee Maker", "Automatic drip coffee maker", 
                     new BigDecimal("89.99"), 20, "https://images.unsplash.com/photo-1495474472287-4d71bcdd2085?w=400", home);
        
        createProduct("Garden Tools Set", "Complete set of gardening tools", 
                     new BigDecimal("149.99"), 25, "https://images.unsplash.com/photo-1416879595882-3373a0480b5b?w=400", home);
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