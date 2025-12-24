package com.example.SpringBootRest;

import com.example.SpringBootRest.entity.Product;
import com.example.SpringBootRest.repository.ProductRepository;
import com.example.SpringBootRest.serviceImpl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = Product.builder()
                .id(1L)
                .name("Test Product")
                .description("Test Description")
                .price(99.99)
                .build();
    }

    @Test
    void createProduct_ShouldReturnCreatedProduct() {
        // given
        given(productRepository.save(any(Product.class))).willReturn(product);

        // when
        Product savedProduct = productService.createProduct(product);

        // then
        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct.getId()).isEqualTo(1L);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void getProductById_ShouldReturnProduct_WhenProductExists() {
        // given
        given(productRepository.findById(1L)).willReturn(Optional.of(product));

        // when
        Product foundProduct = productService.getProductById(1L);

        // then
        assertThat(foundProduct).isNotNull();
        assertThat(foundProduct.getId()).isEqualTo(1L);
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void getProductById_ShouldThrowException_WhenProductNotFound() {
        // given
        given(productRepository.findById(1L)).willReturn(Optional.empty());

        // when & then
        assertThrows(java.util.NoSuchElementException.class, () -> {
            productService.getProductById(1L);
        });
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void getAllProducts_ShouldReturnAllProducts() {
        // given
        Product product2 = Product.builder()
                .id(2L)
                .name("Another Product")
                .description("Another Description")
                .price(199.99)
                .build();
        
        given(productRepository.findAll()).willReturn(Arrays.asList(product, product2));

        // when
        List<Product> products = productService.getAllProducts();

        // then
        assertThat(products).isNotEmpty();
        assertThat(products.size()).isEqualTo(2);
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void updateProduct_ShouldReturnUpdatedProduct_WhenProductExists() {
        // given
        Product updatedProduct = Product.builder()
                .id(1L)
                .name("Updated Product")
                .description("Updated Description")
                .price(199.99)
                .build();

        given(productRepository.findById(1L)).willReturn(Optional.of(product));
        given(productRepository.save(any(Product.class))).willReturn(updatedProduct);

        // when
        Product result = productService.updateProduct(updatedProduct);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Updated Product");
        assertThat(result.getDescription()).isEqualTo("Updated Description");
        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).save(any(Product.class));
    }

//    @Test
//    void deleteProduct_ShouldDeleteProduct_WhenProductExists() {
//        // given
//        long productId = 1L;
//        given(productRepository.existsById(productId)).willReturn(true);
//        willDoNothing().given(productRepository).deleteById(productId);
//
//        // when
//        productService.deleteProduct(productId);
//
//        // then
//        verify(productRepository, times(1)).deleteById(productId);
//    }

//    @Test
//    void createProduct_ShouldThrowException_WhenProductIsNull() {
//        // given
//        Product nullProduct = null;
//
//        // when & then
//        assertThrows(NullPointerException.class, () -> {
//            productService.createProduct(nullProduct);
//        });
//        verify(productRepository, never()).save(any(Product.class));
//    }

    @Test
    void updateProduct_ShouldThrowException_WhenProductNotFound() {
        // given
        Product nonExistentProduct = Product.builder()
                .id(999L)
                .name("Non-existent Product")
                .description("This product doesn't exist")
                .price(99.99)
                .build();

        given(productRepository.findById(999L)).willReturn(Optional.empty());

        // when & then
        assertThrows(java.util.NoSuchElementException.class, () -> {
            productService.updateProduct(nonExistentProduct);
        });
        verify(productRepository, times(1)).findById(999L);
        verify(productRepository, never()).save(any(Product.class));
    }
}
