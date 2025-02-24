package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String location;
    private Double rating;

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL)
    private List<SupplierProduct> supplierProducts;
}

