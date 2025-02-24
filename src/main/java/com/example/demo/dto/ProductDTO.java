package com.example.demo.dto;

import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private String category;
    private Double price;       
    private Integer stock;      
    private Integer deliveryTime; 
    private Long supplierId; 
    private List<SupplierProductDTO> suppliers;
}
