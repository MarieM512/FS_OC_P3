package com.mariemetay.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserRegisterDTO {

    @Schema(example = "John", required = true)
    private String name;

    @Schema(example = "user@email.com", required = true)
    private String email;

    @Schema(example = "password", required = true)
    private String password;
}
