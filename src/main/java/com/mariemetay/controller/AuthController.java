package com.mariemetay.controller;

import com.mariemetay.model.User;
import com.mariemetay.service.JWTService;
import com.mariemetay.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JWTService jwtService;

    public AuthController(UserService userService, JWTService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        if (user.getName() == null || user.getEmail() == null || user.getPassword() == null) {
            return ResponseEntity.badRequest().body("invalid");
        } else if (userService.isUserAlreadyExists(user)) {
            return ResponseEntity.badRequest().body("User already exists");
        } else {
            userService.register(user);
            String token = jwtService.generateToken(user);
            return ResponseEntity.ok(token);
        }
    }
}
