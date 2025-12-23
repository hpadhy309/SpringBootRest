package com.example.SpringBootRest.repository;

import com.example.SpringBootRest.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
