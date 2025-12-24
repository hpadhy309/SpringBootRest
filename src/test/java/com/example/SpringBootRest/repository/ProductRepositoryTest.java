package com.example.SpringBootRest.repository;

import com.example.SpringBootRest.entity.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
class ProductRepositoryTest {
    
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductRepository productRepository;

    private Product product;

    @BeforeEach
    void setUp() {
        product = Product.builder()
                .name("Test Product")
                .description("Test Description")
                .price(99.9900)
                .build();
        
        // Clear any existing data
        productRepository.deleteAll();
        entityManager.flush();
    }

    @AfterEach
    void tearDown() {
        productRepository.deleteAll();
    }

    @Test
    void shouldSaveProductSuccessfully() {
        // when
        Product savedProduct = productRepository.save(product);

        // then
        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct.getId()).isNotNull();
        assertThat(savedProduct.getName()).isEqualTo(product.getName());
        assertThat(savedProduct.getDescription()).isEqualTo(product.getDescription());
        assertThat(savedProduct.getPrice()).isEqualTo(product.getPrice());
    }

    @Test
    void shouldFindProductById() {
        // given
        Product savedProduct = productRepository.save(product);

        // when
        Optional<Product> foundProduct = productRepository.findById(savedProduct.getId());

        // then
        assertThat(foundProduct).isPresent();
        assertThat(foundProduct.get().getId()).isEqualTo(savedProduct.getId());
        assertThat(foundProduct.get().getName()).isEqualTo(savedProduct.getName());
    }

    @Test
    void shouldReturnAllProducts() {
        // given
        Product product2 = Product.builder()
                .name("Second Product")
                .description("Second Description")
                .price(199.99)
                .build();

        productRepository.save(product);
        productRepository.save(product2);

        // when
        List<Product> products = productRepository.findAll();

        // then
        assertThat(products).hasSize(2);
        assertThat(products).extracting(Product::getName)
                .containsExactlyInAnyOrder("Test Product", "Second Product");
    }

    @Test
    void shouldUpdateProduct() {
        // given
        Product savedProduct = productRepository.save(product);
        savedProduct.setName("Updated Product");
        savedProduct.setPrice(199.99);

        // when
        Product updatedProduct = productRepository.save(savedProduct);

        // then
        assertThat(updatedProduct.getName()).isEqualTo("Updated Product");
        assertThat(updatedProduct.getPrice()).isEqualTo(199.99);
    }

    @Test
    void shouldDeleteProduct() {
        // given
        Product savedProduct = productRepository.save(product);

        // when
        productRepository.deleteById(savedProduct.getId());

        // then
        Optional<Product> deletedProduct = productRepository.findById(savedProduct.getId());
        assertThat(deletedProduct).isEmpty();
    }

    @Test
    void shouldReturnEmptyWhenProductNotFound() {
        // when
        Optional<Product> notFoundProduct = productRepository.findById(999L);

        // then
        assertThat(notFoundProduct).isEmpty();
    }
}
