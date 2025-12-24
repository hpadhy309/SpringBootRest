package com.example.SpringBootRest.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductTest {

    private Product product;
    private final Long TEST_ID = 1L;
    private final String TEST_NAME = "Test Product";
    private final String TEST_DESCRIPTION = "Test Description";
    private final double TEST_PRICE = 99.99;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(TEST_ID);
        product.setName(TEST_NAME);
        product.setDescription(TEST_DESCRIPTION);
        product.setPrice(TEST_PRICE);
    }

    @Test
    void testProductGetters() {
        assertAll(
            () -> assertThat(product.getId()).isEqualTo(TEST_ID),
            () -> assertThat(product.getName()).isEqualTo(TEST_NAME),
            () -> assertThat(product.getDescription()).isEqualTo(TEST_DESCRIPTION),
            () -> assertThat(product.getPrice()).isEqualTo(TEST_PRICE)
        );
    }

    @Test
    void testProductSetters() {
        // Given
        Long newId = 2L;
        String newName = "Updated Product";
        String newDescription = "Updated Description";
        double newPrice = 199.99;

        // When
        product.setId(newId);
        product.setName(newName);
        product.setDescription(newDescription);
        product.setPrice(newPrice);

        // Then
        assertAll(
            () -> assertThat(product.getId()).isEqualTo(newId),
            () -> assertThat(product.getName()).isEqualTo(newName),
            () -> assertThat(product.getDescription()).isEqualTo(newDescription),
            () -> assertThat(product.getPrice()).isEqualTo(newPrice)
        );
    }

    @Test
    void testNoArgsConstructor() {
        // When
        Product emptyProduct = new Product();
        
        // Then
        assertThat(emptyProduct).isNotNull();
        assertThat(emptyProduct.getId()).isNull();
        assertThat(emptyProduct.getName()).isNull();
        assertThat(emptyProduct.getDescription()).isNull();
        assertThat(emptyProduct.getPrice()).isEqualTo(0.0);
    }

    @Test
    void testAllArgsConstructor() {
        // When
        Product newProduct = new Product(TEST_ID, TEST_NAME, TEST_DESCRIPTION, TEST_PRICE);
        
        // Then
        assertThat(newProduct).isNotNull();
        assertThat(newProduct.getId()).isEqualTo(TEST_ID);
        assertThat(newProduct.getName()).isEqualTo(TEST_NAME);
        assertThat(newProduct.getDescription()).isEqualTo(TEST_DESCRIPTION);
        assertThat(newProduct.getPrice()).isEqualTo(TEST_PRICE);
    }


    /*@Test
    void testEqualsAndHashCode() {
        // Given
        Product sameProduct = new Product(TEST_ID, TEST_NAME, TEST_DESCRIPTION, TEST_PRICE);
        Product differentProduct = new Product(2L, "Different Product", "Different Description", 50.0);
        
        // Then
        assertThat(product).isEqualTo(sameProduct);
        assertThat(product).isNotEqualTo(differentProduct);
        assertThat(product.hashCode()).isEqualTo(sameProduct.hashCode());
    }*/

   /* @Test
    void testToString() {
        // Given
        String expectedToString = "Product(id=1, name=Test Product, description=Test Description, price=99.99)";
        
        // When
        String actualToString = product.toString();
        
        // Then
        assertThat(actualToString).isEqualTo(expectedToString);
    }*/

    @Test
    void testEntityAnnotations() throws NoSuchFieldException {
        // Test @Entity annotation
        assertTrue(Product.class.isAnnotationPresent(jakarta.persistence.Entity.class));
        
        // Test @Table annotation
        assertTrue(Product.class.isAnnotationPresent(jakarta.persistence.Table.class));
       // assertEquals("product", Product.class.getAnnotation(jakarta.persistence.Table.class).name());
        
        // Test @Id annotation
        assertTrue(Product.class.getDeclaredField("id").isAnnotationPresent(jakarta.persistence.Id.class));
        
        // Test @GeneratedValue annotation
        assertTrue(Product.class.getDeclaredField("id").isAnnotationPresent(jakarta.persistence.GeneratedValue.class));
        
        // Test @Column annotations
        java.lang.reflect.Field nameField = Product.class.getDeclaredField("name");
        assertTrue(nameField.isAnnotationPresent(jakarta.persistence.Column.class));
        assertFalse(nameField.getAnnotation(jakarta.persistence.Column.class).nullable());
        
        java.lang.reflect.Field descriptionField = Product.class.getDeclaredField("description");
        assertTrue(descriptionField.isAnnotationPresent(jakarta.persistence.Column.class));
        assertFalse(descriptionField.getAnnotation(jakarta.persistence.Column.class).nullable());
        
        java.lang.reflect.Field priceField = Product.class.getDeclaredField("price");
        assertTrue(priceField.isAnnotationPresent(jakarta.persistence.Column.class));
        assertFalse(priceField.getAnnotation(jakarta.persistence.Column.class).nullable());
    }
}
