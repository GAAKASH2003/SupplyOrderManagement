package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Product;
import com.example.demo.model.Supplier;
import com.example.demo.model.SupplierProduct;

public interface SupplierProductRepo extends JpaRepository<SupplierProduct, Long> {
    List<SupplierProduct> findByProductId(Long productId);
    boolean existsBySupplierAndProduct(Supplier supplier, Product product);
    void deleteByProduct(Product product);
    void deleteBySupplierId(Long supplierId);
    Optional<SupplierProduct> findBySupplierAndProduct(Supplier supplier, Product product);
    List<SupplierProduct> findByProductAndStockGreaterThanEqual(Product product, int quantity);
  

}
