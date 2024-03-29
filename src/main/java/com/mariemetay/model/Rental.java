package com.mariemetay.model;

import java.math.BigInteger;
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
@Table(name = "RENTALS")
@Data
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(example = "1")
    private Long id;

    @Schema(example = "house")
    private String name;

    @Schema(example = "100")
    private BigInteger surface;

    @Schema(example = "780")
    private BigInteger price;

    @Schema(example = "http://localhost:4200/..")
    private String picture;

    @Schema(example = "Beautiful home")
    private String description;

    @Column(name = "owner_id")
    @Schema(example = "2")
    private Long ownerId;

    @Column(name = "created_at")
    @Schema(example = "2024-03-28 12:29:44")
    private Date createdAt;

    @Column(name = "updated_at")
    @Schema(example = "2024-03-28 12:29:44")
    private Date updatedAt;

}
