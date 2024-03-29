package com.mariemetay.model.dto;

import java.math.BigInteger;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class RentalDTO {

    private String name;

    private BigInteger surface;

    private BigInteger price;

    private MultipartFile picture;

    private String description;

}
