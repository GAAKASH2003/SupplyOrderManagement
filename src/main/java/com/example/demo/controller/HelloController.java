package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import jakarta.servlet.http.HttpServletRequest;

@RestController
public class HelloController {
    @RequestMapping("/")
    public String greet(HttpServletRequest req){
        return "Welcome"+req.getSession().getId();
    }
}
