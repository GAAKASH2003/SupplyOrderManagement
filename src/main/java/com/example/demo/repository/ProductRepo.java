package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import com.example.demo.model.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    
    Optional <Product> findByName(String name);
    
}
