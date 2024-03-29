package com.mariemetay.model.dto;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserDTO {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "John")
    private String name;

    @Schema(example = "user@email.com")
    private String email;

    @Schema(example = "2024-03-28 12:29:44")
    private Date createdAt;

    @Schema(example = "2024-03-28 12:29:44")
    private Date updatedAt;

}
