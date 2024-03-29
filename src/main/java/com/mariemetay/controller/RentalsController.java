package com.mariemetay.controller;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mariemetay.model.Rental;
import com.mariemetay.model.User;
import com.mariemetay.model.dto.RentalDTO;
import com.mariemetay.model.response.RentalCreate200;
import com.mariemetay.model.response.RentalGetAll200;
import com.mariemetay.service.FileStorageService;
import com.mariemetay.service.JWTService;
import com.mariemetay.service.RentalService;
import com.mariemetay.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@Tag(name = "Rentals", description = "Related to rentals")
@RestController
@RequestMapping("/api/rentals")
public class RentalsController {

    @Autowired
    private JWTService jwtService;

    @Autowired 
    UserService userService;

    @Autowired
    RentalService rentalService;

    @Autowired
    FileStorageService fileStorageService;

    public RentalsController(JWTService jwtService, UserService userService, RentalService rentalService, FileStorageService fileStorageService) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.rentalService = rentalService;
        this.fileStorageService = fileStorageService;
    }

    @Operation(summary = "Create a rental")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully created", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = RentalCreate200.class))
        }),
        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
    })
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<RentalCreate200> create(
        HttpServletRequest request, 
        @RequestPart("name") String name,
        @RequestPart("surface") BigInteger surface,
        @RequestPart("price") BigInteger price,
        @RequestPart("picture") MultipartFile picture,
        @RequestPart("description") String description
    ) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            String token = bearerToken.substring(7);
            String email = jwtService.decodeToken(token);
            User user = userService.getUser(email);
            RentalDTO rental = new RentalDTO();
            rental.setName(name);
            rental.setSurface(surface);
            rental.setPrice(price);
            String pictureUrl = fileStorageService.storeFile(picture);
            rental.setDescription(description);
            rentalService.create(rental, user.getId(), pictureUrl);
            RentalCreate200 response = new RentalCreate200();
            response.setMessage("Rental created !");
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).build();
        }
    }

    @Operation(summary = "Display all rentals")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieve", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = RentalGetAll200.class))
        }),
        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
    })
    @GetMapping("")
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<RentalGetAll200> getAllRentals(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            RentalGetAll200 reponse = new RentalGetAll200();
            reponse.setRentals(rentalService.getAllRentals());
            return ResponseEntity.ok(reponse);
        } else {
            return ResponseEntity.status(401).build();
        }
    }

}
