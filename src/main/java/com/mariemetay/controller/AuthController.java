package com.mariemetay.controller;

import com.mariemetay.model.User;
import com.mariemetay.model.dto.UserLoginDTO;
import com.mariemetay.model.dto.UserRegisterDTO;
import com.mariemetay.model.response.Login401;
import com.mariemetay.model.response.RegisterLogin200;
import com.mariemetay.service.JWTService;
import com.mariemetay.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth", description = "Related to authentication")
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

    @Operation(summary = "Register a new user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful registration", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = RegisterLogin200.class))
        }),
        @ApiResponse(responseCode = "400", description = "Some fields are empty", content = @Content),
        @ApiResponse(responseCode = "409", description = "User already exists", content = @Content)
    })
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegisterDTO user) {
        if (user.getName() == null || user.getEmail() == null || user.getPassword() == null) {
            return ResponseEntity.badRequest().build();
        } else if (userService.isUserAlreadyExists(user)) {
            return ResponseEntity.status(409).build();
        } else {
            userService.register(user);
            String token = jwtService.generateToken(user.getEmail());
            RegisterLogin200 response = new RegisterLogin200();
            response.setToken(token);
            return ResponseEntity.ok(response);
        }
    }

    @Operation(summary = "Connect user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful login", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = RegisterLogin200.class))
        }),
        @ApiResponse(responseCode = "400", description = "Some fields are empty", content = @Content),
        @ApiResponse(responseCode = "401", description = "Invalid email or password ", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Login401.class))
        })
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDTO user) {
        if (user.getEmail() == null || user.getPassword() == null) {
            return ResponseEntity.badRequest().build();
        } else if (userService.canConnect(user)) {
            String token = jwtService.generateToken(user.getEmail());
            RegisterLogin200 response = new RegisterLogin200();
            response.setToken(token);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body(new Login401());
        }
    }

    @Operation(summary = "Get user information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retrieve all informations about the user", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))
        }),
        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
    })
    @GetMapping("/me")
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<User> getUser(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            String token = bearerToken.substring(7);
            String email = jwtService.decodeToken(token);
            User user = userService.getUser(email);
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(401).build();
        }
    }
}
