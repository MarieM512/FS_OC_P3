package com.mariemetay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mariemetay.model.Rental;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {}
