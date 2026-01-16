package com.carrent.domain.service;

import com.carrent.domain.model.aggregate.Car;
import com.carrent.domain.model.aggregate.Customer;
import com.carrent.domain.model.aggregate.Rental;
import com.carrent.domain.model.valueobject.RentalPeriod;
import com.carrent.domain.repository.RentalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentalValidationService {

    private final RentalRepository rentalRepository;

    public RentalValidationService(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public void validateRental(Customer customer, Car car, RentalPeriod rentalPeriod) {
        validateCustomer(customer);
        validateCar(car);
        validateRentalPeriod(rentalPeriod);
        validateNoOverlappingRentals(car, rentalPeriod);
    }

    private void validateCustomer(Customer customer) {
        if (!customer.isActive()) {
            throw new IllegalStateException("Customer is not active");
        }
        if (!customer.isLicenseValid()) {
            throw new IllegalStateException("Customer license is expired or invalid");
        }
    }

    private void validateCar(Car car) {
        if (!car.isAvailable()) {
            throw new IllegalStateException("Car is not available for rent");
        }
    }

    private void validateRentalPeriod(RentalPeriod rentalPeriod) {
        if (rentalPeriod.getDays() > 30) {
            throw new IllegalArgumentException("Rental period cannot exceed 30 days");
        }
    }

    private void validateNoOverlappingRentals(Car car, RentalPeriod rentalPeriod) {
        List<Rental> activeRentals = rentalRepository.findActiveRentalsByCarId(car.getId());
        for (Rental rental : activeRentals) {
            if (rental.getRentalPeriod().overlaps(rentalPeriod)) {
                throw new IllegalStateException("Car is already rented for the requested period");
            }
        }
    }
}
