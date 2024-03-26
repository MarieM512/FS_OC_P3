package com.mariemetay.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.mariemetay.services.JWTService;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
public class AuthController {

    private JWTService jwtService;

    public AuthController(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    // For test
    @PostMapping("/login")
    public String register(Authentication authentication) {
        String token = jwtService.generateToken(authentication);
        return token;
    }

    // For test
    @GetMapping("/")
    public String getResource() {
        return "a value...";
    }
}
