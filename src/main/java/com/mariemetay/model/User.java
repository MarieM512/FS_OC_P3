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
@Table(name = "USERS")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(example = "1")
    private Long id;

    @Schema(example = "user@email.com")
    private String email;

    @Schema(example = "John")
    private String name;

    @Schema(example = "password")
    private String password;

    @Column(name = "created_at")
    @Schema(example = "2024-03-28 12:29:44")
    private Date createdAt;

    @Column(name = "updated_at")
    @Schema(example = "2024-03-28 12:29:44")
    private Date updatedAt;

}
