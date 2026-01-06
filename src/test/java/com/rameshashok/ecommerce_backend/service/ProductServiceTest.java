package com.rameshashok.ecommerce_backend.service;

import com.rameshashok.ecommerce_backend.dto.ProductResponse;
import com.rameshashok.ecommerce_backend.entity.Category;
import com.rameshashok.ecommerce_backend.entity.Product;
import com.rameshashok.ecommerce_backend.exception.ResourceNotFoundException;
import com.rameshashok.ecommerce_backend.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product product;
    private Category category;

    @BeforeEach
    void setUp() {
        category = new Category();
        category.setId(1L);
        category.setName("Electronics");

        product = new Product();
        product.setId(1L);
        product.setName("Smartphone");
        product.setPrice(BigDecimal.valueOf(699.99));
        product.setStockQuantity(10);
        product.setCategory(category);
    }

    @Test
    void getAllProducts_ShouldReturnAllProducts() {
        List<Product> products = Arrays.asList(product);
        when(productRepository.findAll()).thenReturn(products);

        List<ProductResponse> result = productService.getAllProducts();

        assertEquals(1, result.size());
        assertEquals(product.getName(), result.get(0).getName());
        verify(productRepository).findAll();
    }

    @Test
    void getAllProductsWithPageable_ShouldReturnPagedProducts() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> productPage = new PageImpl<>(Arrays.asList(product));
        when(productRepository.findAll(pageable)).thenReturn(productPage);

        Page<ProductResponse> result = productService.getAllProducts(pageable);

        assertEquals(1, result.getContent().size());
        assertEquals(product.getName(), result.getContent().get(0).getName());
        verify(productRepository).findAll(pageable);
    }

    @Test
    void getProductById_WhenExists_ShouldReturnProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        ProductResponse result = productService.getProductById(1L);

        assertEquals(product.getName(), result.getName());
        verify(productRepository).findById(1L);
    }

    @Test
    void getProductById_WhenNotExists_ShouldThrowException() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.getProductById(1L));
        verify(productRepository).findById(1L);
    }

    @Test
    void getProductsByCategory_ShouldReturnProductsInCategory() {
        List<Product> products = Arrays.asList(product);
        when(productRepository.findByCategoryId(1L)).thenReturn(products);

        List<ProductResponse> result = productService.getProductsByCategory(1L);

        assertEquals(1, result.size());
        assertEquals(product.getName(), result.get(0).getName());
        verify(productRepository).findByCategoryId(1L);
    }

    @Test
    void searchProductsByName_ShouldReturnMatchingProducts() {
        List<Product> products = Arrays.asList(product);
        when(productRepository.findByNameContainingIgnoreCase("phone")).thenReturn(products);

        List<ProductResponse> result = productService.searchProductsByName("phone");

        assertEquals(1, result.size());
        assertEquals(product.getName(), result.get(0).getName());
        verify(productRepository).findByNameContainingIgnoreCase("phone");
    }

    @Test
    void createProduct_ShouldSaveAndReturnProduct() {
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product result = productService.createProduct(product);

        assertEquals(product.getName(), result.getName());
        verify(productRepository).save(product);
    }

    @Test
    void updateProduct_WhenExists_ShouldUpdateAndReturnProduct() {
        Product updatedDetails = new Product();
        updatedDetails.setName("Updated Phone");
        updatedDetails.setPrice(BigDecimal.valueOf(799.99));
        updatedDetails.setStockQuantity(15);
        updatedDetails.setCategory(category);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product result = productService.updateProduct(1L, updatedDetails);

        assertEquals("Updated Phone", product.getName());
        assertEquals(BigDecimal.valueOf(799.99), product.getPrice());
        verify(productRepository).findById(1L);
        verify(productRepository).save(product);
    }

    @Test
    void updateProduct_WhenNotExists_ShouldThrowException() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> 
            productService.updateProduct(1L, new Product()));
        verify(productRepository).findById(1L);
        verify(productRepository, never()).save(any());
    }

    @Test
    void deleteProduct_WhenExists_ShouldDeleteProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        productService.deleteProduct(1L);

        verify(productRepository).findById(1L);
        verify(productRepository).delete(product);
    }

    @Test
    void deleteProduct_WhenNotExists_ShouldThrowException() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.deleteProduct(1L));
        verify(productRepository).findById(1L);
        verify(productRepository, never()).delete(any());
    }
}