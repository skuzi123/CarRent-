package com.carrent.boundedcontext.rental.domain.service;

import com.carrent.boundedcontext.rental.domain.model.aggregate.Rental;
import com.carrent.boundedcontext.rental.domain.model.valueobject.RentalPeriod;
import com.carrent.boundedcontext.rental.domain.repository.RentalRepository;

import java.util.List;

public class RentalValidationService {

    private final RentalRepository rentalRepository;

    public RentalValidationService(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public void validateRental(Long customerId, Long carId, RentalPeriod rentalPeriod) {
        validateIds(customerId, carId);
        validateRentalPeriod(rentalPeriod);
        validateNoOverlappingRentals(carId, rentalPeriod);
    }

    private void validateIds(Long customerId, Long carId) {
        if (customerId == null) {
            throw new IllegalArgumentException("Customer ID cannot be null");
        }
        if (carId == null) {
            throw new IllegalArgumentException("Car ID cannot be null");
        }
    }

    private void validateRentalPeriod(RentalPeriod rentalPeriod) {
        if (rentalPeriod.getDays() > 30) {
            throw new IllegalArgumentException("Rental period cannot exceed 30 days");
        }
    }

    private void validateNoOverlappingRentals(Long carId, RentalPeriod rentalPeriod) {
        List<Rental> activeRentals = rentalRepository.findActiveRentalsByCarId(carId);
        for (Rental rental : activeRentals) {
            if (rental.getRentalPeriod().overlaps(rentalPeriod)) {
                throw new IllegalStateException("Car is already rented for the requested period");
            }
        }
    }
}
