package com.example.SpringBootRest.serviceImpl;

import com.example.SpringBootRest.ProductService;
import com.example.SpringBootRest.entity.Product;
import com.example.SpringBootRest.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.SpringBootRest.repository.ProductRepository.*;

@Service
@AllArgsConstructor

public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
     Product updateProduct=  productRepository.findById(product.getId()).get();
     updateProduct.setName(product.getName());
     updateProduct.setDescription(product.getDescription());
     updateProduct.setPrice(product.getPrice());
     return    updateProduct;

    }

    @Override
    public Product getProductById(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        return optionalProduct.get();
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }


}
