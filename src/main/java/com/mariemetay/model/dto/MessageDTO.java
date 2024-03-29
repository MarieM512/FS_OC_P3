package com.mariemetay.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class MessageDTO {

    @Schema(example = "Need more informations", required = true)
    private String message;

    @Schema(example = "2", required = true)
    private Integer user_id;
    
    @Schema(example = "7", required = true)
    private Integer rental_id;

}
