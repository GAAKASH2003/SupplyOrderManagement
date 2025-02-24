package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Users;

public interface UserRepo extends JpaRepository<Users,Integer> {

    Users findByUsername(String username);

}
