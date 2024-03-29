package com.mariemetay.controller;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mariemetay.model.User;
import com.mariemetay.model.dto.RentalDTO;
import com.mariemetay.model.response.RentalCreate200;
import com.mariemetay.service.FileStorageService;
import com.mariemetay.service.JWTService;
import com.mariemetay.service.RentalService;
import com.mariemetay.service.UserService;

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

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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

    // @GetMapping("")
    // public ResponseEntity<List<Rental>> getAllRentals(HttpServletRequest request) {
        // String bearerToken = request.getHeader("Authorization");
        // if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
        //     String token = bearerToken.substring(7);
        //     String email = jwtService.decodeToken(token);
        //     User user = userService.getUser(email);
        //     return ResponseEntity.ok(user);
        // } else {
        //     return ResponseEntity.status(401).build();
        // }
    // }

}
