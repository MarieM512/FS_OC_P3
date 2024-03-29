package com.mariemetay.model.dto;

import java.util.Date;

import lombok.Data;

@Data
public class UserDTO {

    private Long id;
    private String name;
    private String email;
    private Date createdAt;
    private Date updatedAt;

}
