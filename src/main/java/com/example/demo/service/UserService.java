package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.dto.LoginDTO;
import com.example.demo.model.Users;
import com.example.demo.repository.UserRepo;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(5);
    // @Autowired
    // private AuthenticationManager authenticationManager;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

    public Users register(Users user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    public String verify(@RequestBody Users user) {
        System.out.println(user.getUsername());
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(user.getUsername());
        } else {
            return "fail";
        }
    }

    // public String verify(Users user) {
    // System.out.println(user.getUsername());
    // Authentication authentication = authenticationManager
    // .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),
    // user.getPassword()));

    // return "Invalid Password";

    // }
}
