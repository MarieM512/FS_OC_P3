package com.mariemetay.service;

import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mariemetay.model.Rental;
import com.mariemetay.model.dto.RentalDTO;
import com.mariemetay.repository.RentalRepository;

@Service
public class RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private ModelMapper modelMapper;

    public RentalService(RentalRepository rentalRepository, ModelMapper modelMapper) {
        this.rentalRepository = rentalRepository;
        this.modelMapper = modelMapper;
    }

    public Rental create(RentalDTO rentalDTO, Long id, String picture) {
        Rental rental = createDtoToEntity(rentalDTO);
        rental.setPicture(picture);
        rental.setOwnerId(id);
        rental.setCreatedAt(new Date());
        return rentalRepository.save(rental);
    }

    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }

    public Rental getRentalById(Long id) {
        return rentalRepository.findById(id).orElse(null);
    }

    public Rental updateRental(Rental rental) {
        Rental actualRental = getRentalById(rental.getId());
        if (rental.getName() != null) {
            actualRental.setName(rental.getName());
        }
        if (rental.getSurface() != null) {
            actualRental.setSurface(rental.getSurface());
        }
        if (rental.getPrice() != null) {
            actualRental.setPrice(rental.getPrice());
        }
        if (rental.getDescription() != null) {
            actualRental.setDescription(rental.getDescription());
        }
        return rentalRepository.save(actualRental);
    }

    private Rental createDtoToEntity(RentalDTO rentalDTO) {
        return modelMapper.map(rentalDTO, Rental.class);
    }

}
