package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import com.example.demo.dto.ProductDTO;
import com.example.demo.dto.SupplierDTO;
import com.example.demo.model.Product;
import com.example.demo.model.Supplier;
import com.example.demo.model.SupplierProduct;
import com.example.demo.repository.ProductRepo;
import com.example.demo.repository.SupplierRepo;

@Service
public class SupplierService {
    @Autowired
    private SupplierRepo supplierRepo;
    @Autowired
    private ProductRepo productRepo;

    public SupplierDTO getSupplierById(Long id) {
        return supplierRepo.findById(id).map(supplier -> {
            SupplierDTO supplierDTO = new SupplierDTO();
            supplierDTO.setId(supplier.getId());
            supplierDTO.setName(supplier.getName());
            supplierDTO.setLocation(supplier.getLocation());
            supplierDTO.setRating(supplier.getRating());

            List<ProductDTO> products = supplier.getSupplierProducts().stream().map(sp -> {
                ProductDTO productDTO = new ProductDTO();
                productDTO.setId(sp.getProduct().getId());
                productDTO.setName(sp.getProduct().getName());
                productDTO.setCategory(sp.getProduct().getCategory());
                return productDTO;
            }).collect(Collectors.toList());

            supplierDTO.setProducts(products);
            return supplierDTO;
        }).orElse(null);
    }

    public SupplierDTO registerSupplier(SupplierDTO supplierDTO) {
        Supplier supplier = new Supplier();
        supplier.setName(supplierDTO.getName());
        supplier.setLocation(supplierDTO.getLocation());
        supplier.setRating(supplierDTO.getRating());

        Supplier savedsupplier = supplierRepo.save(supplier);
        List<SupplierProduct> supplierProducts = supplierDTO.getProducts().stream().map(productDTO -> {
            Product product;
            if (productDTO.getId() != null) {
                product = productRepo.findById(productDTO.getId()).orElse(null);
            } else {
                product = productRepo.findByName(productDTO.getName()).orElseGet(() -> {
                    Product newProduct = new Product();
                    newProduct.setName(productDTO.getName());
                    newProduct.setCategory(productDTO.getCategory());
                    return productRepo.save(newProduct);
                });
            }
            if (product == null) {
                product = new Product();
                product.setName(productDTO.getName());
                product.setCategory(productDTO.getCategory());
                product = productRepo.save(product); // Save the new product
            }
            // Create SupplierProduct mapping
            SupplierProduct supplierProduct = new SupplierProduct();
            supplierProduct.setSupplier(savedsupplier);
            supplierProduct.setProduct(product);
            supplierProduct.setPrice(productDTO.getPrice());
            supplierProduct.setStock(productDTO.getStock());
            supplierProduct.setDeliveryTime(productDTO.getDeliveryTime());
            return supplierProduct;
        }).collect(Collectors.toList());
        savedsupplier.setSupplierProducts(supplierProducts);
        supplierRepo.save(savedsupplier);

        return supplierRepo.findById(supplier.getId()).map(s -> {
            SupplierDTO dto = new SupplierDTO();
            dto.setId(s.getId());
            dto.setName(s.getName());
            dto.setLocation(s.getLocation());
            dto.setRating(s.getRating());
            return dto;
        }).orElse(null);
    }

    public Object getSupplierProducts(Long id) {
        return supplierRepo.findById(id).map(supplier -> {
            List<ProductDTO> products = supplier.getSupplierProducts().stream().map(sp -> {
                ProductDTO productDTO = new ProductDTO();
                productDTO.setId(sp.getProduct().getId());
                productDTO.setName(sp.getProduct().getName());
                productDTO.setCategory(sp.getProduct().getCategory());
                productDTO.setPrice(sp.getPrice());
                productDTO.setStock(sp.getStock());
                productDTO.setDeliveryTime(sp.getDeliveryTime());
                return productDTO;
            }).collect(Collectors.toList());
            return products;
        }).orElse(null);
    }
    

}
