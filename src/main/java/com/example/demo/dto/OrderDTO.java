package com.example.demo.dto;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long id;  
    private Long customerId;
    private Long productId;  // ID of the ordered product
    private Long supplierId; // Assigned supplier (filled after supplier selection)
    private int quantity;    // Quantity ordered
    private String status;   // Order status (Pending, Confirmed, Shipped, Delivered, Canceled)
    private LocalDateTime orderDate; // Order timestamp
}

