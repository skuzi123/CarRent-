package com.carrent.boundedcontext.rental.domain.repository;

import com.carrent.boundedcontext.rental.domain.model.aggregate.Rental;

import java.util.List;
import java.util.Optional;

public interface RentalRepository {
    Rental save(Rental rental);
    Optional<Rental> findById(Long id);
    List<Rental> findAll();
    List<Rental> findByCustomerId(Long customerId);
    List<Rental> findActiveRentalsByCarId(Long carId);
    void deleteById(Long id);
}
