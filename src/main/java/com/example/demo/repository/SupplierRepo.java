package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Supplier;

public interface SupplierRepo extends JpaRepository<Supplier, Long> {
    
}
