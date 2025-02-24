package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import com.example.demo.dto.SupplierProductDTO;
import com.example.demo.model.Product;
import com.example.demo.model.SupplierProduct;
import com.example.demo.repository.ProductRepo;
import com.example.demo.repository.SupplierProductRepo;

@Service
public class SupplierProductService {
    @Autowired
    private SupplierProductRepo supplierProductRepo;
    @Autowired
    private ProductRepo ProductRepo;

    public List<SupplierProductDTO> getAllSupplierProducts() {
        List<SupplierProduct> supplierProducts = supplierProductRepo.findAll();

        return supplierProducts.stream().map(sp -> 
            new SupplierProductDTO(
                sp.getSupplier().getId(),
                sp.getSupplier().getName(),
                sp.getProduct().getId(),
                sp.getProduct().getName(),
                sp.getPrice(),
                sp.getStock(),
                sp.getDeliveryTime()
            )
        ).collect(Collectors.toList());
    }
    public List<SupplierProductDTO> getMatchingSupplierProducts(long productId, int quantity) {
        Product product =ProductRepo.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        List<SupplierProduct> supplierProducts = supplierProductRepo.findByProductAndStockGreaterThanEqual(product, quantity);

        return supplierProducts.stream().map(sp -> 
            new SupplierProductDTO(
                sp.getSupplier().getId(),
                sp.getSupplier().getName(),
                sp.getProduct().getId(),
                sp.getProduct().getName(),
                sp.getPrice(),
                sp.getStock(),
                sp.getDeliveryTime()
            )
        ).collect(Collectors.toList());

    }

    
}
