package com.example.SpringBootRest.controller;

import com.example.SpringBootRest.ProductService;
import com.example.SpringBootRest.entity.Product;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@AllArgsConstructor
public class ProductController {
    private ProductService productService;

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product){
        Product saveProduct=productService.createProduct(product);
        return new ResponseEntity<>(saveProduct, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long PId,@RequestBody Product product){
product.setId(PId);
        Product updateProduct = productService.updateProduct(product);
        return new ResponseEntity<>(updateProduct, HttpStatus.OK);
    }

    // Build Update User REST API
//    @PutMapping("{id}")
//    // http://localhost:8080/api/users/1
//    public ResponseEntity<User> updateUser(@PathVariable("id") Long userId,
//                                           @RequestBody User user){
//        user.setId(userId);
//        User updatedUser = userService.updateUser(user);
//        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
//    }OK





    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long PId){
        Product product=productService.getProductById(PId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts(){
        List<Product> products=productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Long ProductId){
        productService.deleteProduct(ProductId);
        return new ResponseEntity<>("Product successfully deleted!", HttpStatus.OK);
    }


}
