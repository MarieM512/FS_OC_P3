package com.mariemetay.model;

import java.math.BigInteger;
import java.util.Date;

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
    // @Schema(example = "1")
    private Long id;

    // @Schema(example = "house")
    private String name;

    private BigInteger surface;

    private BigInteger price;

    private String picture;

    private String description;

    @Column(name = "owner_id")
    private Long ownerId;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

}
