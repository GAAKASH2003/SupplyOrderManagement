package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CustomerDTO;
import com.example.demo.model.Customer;
import com.example.demo.repository.CustomerRepo;


@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    @Autowired
    private CustomerRepo customerRepo;
    @RequestMapping("/")
    public ResponseEntity<?> registerCustomer(@RequestBody CustomerDTO customerDTO) {
        try {
            String name = customerDTO.getName();
            if (customerRepo.existsByName(name)) {
                return ResponseEntity.badRequest().body("Customer with this email already exists");
            }
            Customer customer = new Customer();
            customer.setName(name);
            Customer savedCustomer = customerRepo.save(customer);
            return ResponseEntity.ok(savedCustomer);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error registering customer: " + e.getMessage());
        }
    }
}
