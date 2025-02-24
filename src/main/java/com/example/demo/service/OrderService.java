package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.OrderDTO;
import com.example.demo.model.Customer;
import com.example.demo.model.Order;
import com.example.demo.model.Product;
import com.example.demo.model.SupplierProduct;
import com.example.demo.repository.CustomerRepo;
import com.example.demo.repository.OrderRepo;
import com.example.demo.repository.ProductRepo;
import com.example.demo.repository.SupplierProductRepo;

import jakarta.transaction.Transactional;

@Service
public class OrderService {
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private SupplierProductRepo supplierProductRepo;
    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    private ProductRepo productRepo;

    public OrderDTO placeOrder(OrderDTO orderDTO) {
        Customer customer = customerRepo.findById(orderDTO.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Product product = productRepo.findById(orderDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        List<SupplierProduct> availableSuppliers = supplierProductRepo.findByProductAndStockGreaterThanEqual(product,
                orderDTO.getQuantity());
        if (availableSuppliers.isEmpty()) {
            throw new RuntimeException("No supplier available with enough stock");
        }
        SupplierProduct bestSupplierProduct = availableSuppliers.stream()
                .sorted(Comparator.comparing(SupplierProduct::getPrice)
                        .thenComparing(SupplierProduct::getDeliveryTime)
                        .thenComparing(sp -> sp.getSupplier().getRating(), Comparator.reverseOrder()))
                .findFirst().orElseThrow();
        Order order = new Order();
        order.setCustomer(customer);
        order.setProduct(product);
        order.setQuantity(orderDTO.getQuantity());
        order.setStatus("Pending");
        order.setSupplier(bestSupplierProduct.getSupplier());
        order.setOrderDate(LocalDateTime.now());

        order = orderRepo.save(order);
        return new OrderDTO(
                order.getId(),
                order.getCustomer().getId(),
                order.getProduct().getId(),
                order.getSupplier().getId(),
                order.getQuantity(),
                order.getStatus(),
                order.getOrderDate());
    }

    @Transactional
    public void updateOrderStatus(Long orderId, String status) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        if(status.equals("Confirmed")){
            SupplierProduct supplierProduct = supplierProductRepo.findBySupplierAndProduct(order.getSupplier(), order.getProduct())
                    .orElseThrow(() -> new RuntimeException("Supplier product not found"));
            supplierProduct.setStock(supplierProduct.getStock() - order.getQuantity());
            supplierProductRepo.save(supplierProduct);

        }    
        if (!List.of("Confirmed", "Shipped", "Delivered", "Canceled").contains(status)) {
            throw new IllegalArgumentException("Invalid status");
        }
        order.setStatus(status);
        orderRepo.save(order);
    }
     public OrderDTO getOrderById(Long orderId) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return new OrderDTO(order.getId(), order.getCustomer().getId(), order.getProduct().getId(),
                order.getSupplier().getId(), order.getQuantity(), order.getStatus(), order.getOrderDate());
    }

    public List<OrderDTO> getAllOrders() {
        return orderRepo.findAll().stream().map(order ->
                new OrderDTO(order.getId(), order.getCustomer().getId(), order.getProduct().getId(),
                        order.getSupplier().getId(), order.getQuantity(), order.getStatus(), order.getOrderDate()))
                .collect(Collectors.toList());
    }
    

}
