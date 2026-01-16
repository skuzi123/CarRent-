package com.carrent.application.mapper;

import com.carrent.application.dto.RentalDTO;
import com.carrent.domain.model.aggregate.Rental;
import org.springframework.stereotype.Component;

@Component
public class RentalMapper {

    public RentalDTO toDTO(Rental rental) {
        if (rental == null) {
            return null;
        }
        return new RentalDTO(
                rental.getId(),
                rental.getCustomer().getId(),
                rental.getCustomer().getFirstName() + " " + rental.getCustomer().getLastName(),
                rental.getCar().getId(),
                rental.getCar().getSpecification().getMake() + " " + 
                rental.getCar().getSpecification().getModel() + " " + 
                rental.getCar().getSpecification().getYear(),
                rental.getRentalPeriod().getStartDate(),
                rental.getRentalPeriod().getEndDate(),
                rental.getTotalCost().getAmount(),
                rental.getTotalCost().getCurrencyCode(),
                rental.getStatus().name(),
                rental.getCreatedAt()
        );
    }
}
