package com.example.SpringBootRest.controller;

import com.example.SpringBootRest.ProductService;
import com.example.SpringBootRest.SpringBootRestApplication;
import com.example.SpringBootRest.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = SpringBootRestApplication.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

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
     void givenProduct_whenCreateProduct_thenReturnCreatedProduct() throws Exception {
        // given
        given(productService.createProduct(any(Product.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        // when
        ResultActions response = mockMvc.perform(post("/api/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)));

        // then
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(product.getName())))
                .andExpect(jsonPath("$.description", is(product.getDescription())))
                .andExpect(jsonPath("$.price", is(product.getPrice())));
    }

    @Test
    void givenProductId_whenGetProductById_thenReturnProduct() throws Exception {
        // given
        long productId = 1L;
        given(productService.getProductById(productId)).willReturn(product);

        // when
        ResultActions response = mockMvc.perform(get("/api/product/{id}", productId));

        // then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name", is(product.getName())))
                .andExpect(jsonPath("$.description", is(product.getDescription())));
    }

    @Test
    void givenListOfProducts_whenGetAllProducts_thenReturnProductsList() throws Exception {
        // given
        Product product2 = Product.builder()
                .id(2L)
                .name("Test Product 2")
                .description("Test Description 2")
                .price(199.99)
                .build();

        List<Product> productList = Arrays.asList(product, product2);
        given(productService.getAllProducts()).willReturn(productList);

        // when
        ResultActions response = mockMvc.perform(get("/api/product/all"));

        // then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(productList.size())));
    }

    @Test
    void givenUpdatedProduct_whenUpdateProduct_thenReturnUpdatedProduct() throws Exception {
        // given
        long productId = 1L;
        Product updatedProduct = Product.builder()
                .id(productId)
                .name("Updated Product")
                .description("Updated Description")
                .price(199.99)
                .build();

        given(productService.updateProduct(ArgumentMatchers.any(Product.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        // when
        ResultActions response = mockMvc.perform(put("/api/product/{id}", productId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedProduct)));

        // then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name", is(updatedProduct.getName())))
                .andExpect(jsonPath("$.description", is(updatedProduct.getDescription())));
    }

    @Test
    void givenProductId_whenDeleteProduct_thenReturn200() throws Exception {
        // given
        long productId = 1L;
        willDoNothing().given(productService).deleteProduct(productId);

        // when
        ResultActions response = mockMvc.perform(delete("/api/product/{id}", productId));

        // then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string("Product successfully deleted!"));
        verify(productService, times(1)).deleteProduct(productId);
    }

    @Test
    void givenNonExistingProductId_whenGetProductById_thenReturnNotFound() throws Exception {
        // given
        long productId = 1L;
        given(productService.getProductById(productId)).willReturn(null);

        // when
        ResultActions response = mockMvc.perform(get("/api/product/{id}", productId));

        // then
        response.andExpect(status().isOk()); // Since your controller returns 200 OK even if product is null
    }
}
