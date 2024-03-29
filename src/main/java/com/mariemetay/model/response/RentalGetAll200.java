package com.mariemetay.model.response;

import java.util.List;

import com.mariemetay.model.Rental;

import lombok.Data;

@Data
public class RentalGetAll200 {

    private List<Rental> rentals;

}
