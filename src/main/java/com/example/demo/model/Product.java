package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<SupplierProduct> supplierProducts;

    public Product orElseGet(Object object) {
        
        throw new UnsupportedOperationException("Unimplemented method 'orElseGet'");
    }

 
}
