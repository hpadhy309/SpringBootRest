package com.example.SpringBootRest.serviceImpl;

import com.example.SpringBootRest.entity.Product;
import com.example.SpringBootRest.repository.ProductRepository;
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
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

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
    void shouldSaveProductSuccessfully() {
        // given
        given(productRepository.save(any(Product.class))).willReturn(product);

        // when
        Product savedProduct = productService.createProduct(product);

        // then
        assertThat(savedProduct).isNotNull();
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void shouldReturnAllProducts() {
        // given
        Product product2 = Product.builder()
                .id(2L)
                .name("Second Product")
                .description("Second Description")
                .price(199.99)
                .build();

        given(productRepository.findAll()).willReturn(Arrays.asList(product, product2));

        // when
        List<Product> productList = productService.getAllProducts();

        // then
        assertThat(productList).isNotNull();
        assertThat(productList.size()).isEqualTo(2);
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void shouldFindProductById() {
        // given
        given(productRepository.findById(1L)).willReturn(Optional.of(product));

        // when
        Product foundProduct = productService.getProductById(1L);

        // then
        assertThat(foundProduct).isNotNull();
        assertThat(foundProduct.getId()).isEqualTo(product.getId());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void shouldUpdateProduct() {
        // given
        Product updatedProduct = Product.builder()
                .id(1L)
                .name("Updated Product")
                .description("Updated Description")
                .price(199.99)
                .build();

        given(productRepository.findById(1L)).willReturn(Optional.of(product));
//        given(productRepository.save(any(Product.class))).willReturn(updatedProduct);

        // when
        Product result = productService.updateProduct(updatedProduct);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Updated Product");
        assertThat(result.getDescription()).isEqualTo("Updated Description");
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void shouldDeleteProduct() {
        // given
        long productId = 1L;
        willDoNothing().given(productRepository).deleteById(productId);

        // when
        productService.deleteProduct(productId);

        // then
        verify(productRepository, times(1)).deleteById(productId);
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {
        // given
        long productId = 1L;
        given(productRepository.findById(productId)).willReturn(Optional.empty());

        // when & then
        assertThrows(java.util.NoSuchElementException.class, () -> {
            productService.getProductById(productId);
        });
        verify(productRepository, times(1)).findById(productId);
    }

//    @Test
//    void shouldNotSaveProductWithNullName() {
//        // given
//        Product invalidProduct = Product.builder()
//                .name(null)
//                .description("Test Description")
//                .price(99.99)
//                .build();
//
//        // when & then
//        assertThrows(NullPointerException.class, () -> {
//            productService.createProduct(invalidProduct);
//        });
//    }
}
