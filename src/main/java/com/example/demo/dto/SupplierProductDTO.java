package com.example.demo.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplierProductDTO {
    private Long supplierId;
    private String supplierName;
    private Long productId;
    private String productName;
    private Double price;
    private Integer stock;
    private Integer deliveryTime;
}
