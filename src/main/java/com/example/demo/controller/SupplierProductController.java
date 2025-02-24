package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.SupplierProductService;

@RestController
@RequestMapping("/api/supplier-products")
public class SupplierProductController {
    @Autowired
    private SupplierProductService supplierProductService;

    @GetMapping("/")
    public ResponseEntity<List<com.example.demo.dto.SupplierProductDTO>> getAllSupplierProducts() {
        return ResponseEntity.ok(supplierProductService.getAllSupplierProducts());
    }
    @GetMapping("/{productId}")
    public ResponseEntity<List<com.example.demo.dto.SupplierProductDTO>> getMatchingSupplierProducts(@PathVariable Long productId, @RequestParam int quantity) {
        return ResponseEntity.ok(supplierProductService.getMatchingSupplierProducts(productId, quantity));
    }
}

