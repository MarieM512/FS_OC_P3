package com.mariemetay.model.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class RentalDTO {

    private String name;

    private Integer surface;

    private Integer price;

    private MultipartFile picture;

    private String description;

}
