package com.mariemetay.model;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "MESSAGES")
@Data
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(example = "1")
    private Long id;

    @Column(name = "rental_id")
    @Schema(example = "5")
    private Integer rentalId;

    @Column(name = "user_id")
    @Schema(example = "2")
    private Integer userId;

    @Schema(example = "I would like more informations about it.")
    private String message;

    @Column(name = "created_at")
    @Schema(example = "2024-03-28 12:29:44")
    private Date createdAt;

    @Column(name = "updated_at")
    @Schema(example = "2024-03-28 12:29:44")
    private Date updatedAt;

}
