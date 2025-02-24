package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import com.example.demo.dto.ProductDTO;
import com.example.demo.dto.SupplierProductDTO;
import com.example.demo.model.Product;
import com.example.demo.model.Supplier;
import com.example.demo.model.SupplierProduct;
import com.example.demo.repository.ProductRepo;
import com.example.demo.repository.SupplierProductRepo;
import com.example.demo.repository.SupplierRepo;

import jakarta.transaction.Transactional;

@Service
public class ProductService {
    @Autowired
    private SupplierProductRepo supplierProductRepo;
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private SupplierRepo supplierRepo;

    public List<ProductDTO> getAllProducts() {
        List<SupplierProduct> supplierProducts = supplierProductRepo.findAll();

        return supplierProducts.stream().map(sp -> {
            ProductDTO dto = new ProductDTO();
            dto.setId(sp.getProduct().getId());
            dto.setName(sp.getProduct().getName());
            dto.setCategory(sp.getProduct().getCategory());

            dto.setSuppliers(List.of(new SupplierProductDTO(
                    sp.getSupplier().getId(),
                    sp.getSupplier().getName(),
                    sp.getProduct().getId(),
                    sp.getProduct().getName(),
                    sp.getPrice(),
                    sp.getStock(),
                    sp.getDeliveryTime())));
            return dto;
        }).collect(Collectors.toList());
    }

    public void addProduct(ProductDTO productDTO) {
        Supplier supplier = supplierRepo.findById(productDTO.getSupplierId())
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        String productName = productDTO.getName();

        if (productRepo.findByName(productName).isPresent()) {
            throw new RuntimeException("Product already exists");
        }
        Product product = productRepo.findByName(productDTO.getName()).orElse(null);
        if (product == null) {
            product = new Product();
            product.setName(productDTO.getName());
            product.setCategory(productDTO.getCategory());
            product = productRepo.save(product);
        }
        boolean exists = supplierProductRepo.existsBySupplierAndProduct(supplier, product);
        if (exists) {
         throw new RuntimeException("Product already linked to this supplier");
        }

        SupplierProduct supplierProduct = new SupplierProduct();
        supplierProduct.setSupplier(supplier);
        supplierProduct.setProduct(product);
        supplierProduct.setPrice(productDTO.getPrice());
        supplierProduct.setStock(productDTO.getStock());
        supplierProduct.setDeliveryTime(productDTO.getDeliveryTime());
        supplierProductRepo.save(supplierProduct);
    }
    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    supplierProductRepo.deleteByProduct(product);
    productRepo.delete(product);
    }
    @Transactional
    public void updateProduct(ProductDTO productDTO){
        Product product = productRepo.findById(productDTO.getId())
            .orElseThrow(() -> new RuntimeException("Product not found with id: " + productDTO.getId()));
        Supplier supplier = supplierRepo.findById(productDTO.getSupplierId())
            .orElseThrow(() -> new RuntimeException("Supplier not found"));
        SupplierProduct supplierProduct = supplierProductRepo.findBySupplierAndProduct(supplier, product)
            .orElseThrow(() -> new RuntimeException("SupplierProduct not found"));
        if (productDTO.getName() != null) {
            product.setName(productDTO.getName());
        }
        if (productDTO.getCategory() != null) {
            product.setCategory(productDTO.getCategory());
        }
        if (productDTO.getPrice() != null) {
            supplierProduct.setPrice(productDTO.getPrice());
        }
        if (productDTO.getStock() != null) {
            supplierProduct.setStock(productDTO.getStock());
        }
        if (productDTO.getDeliveryTime() != null) {
            supplierProduct.setDeliveryTime(productDTO.getDeliveryTime());
        }
        
        productRepo.save(product);
        supplierProductRepo.save(supplierProduct);
    }

}
