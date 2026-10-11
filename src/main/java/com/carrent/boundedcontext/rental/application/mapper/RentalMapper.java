package com.carrent.boundedcontext.rental.application.mapper;

import com.carrent.boundedcontext.rental.application.dto.RentalDTO;
import com.carrent.boundedcontext.rental.domain.model.aggregate.Rental;
import org.springframework.stereotype.Component;

@Component
public class RentalMapper {

    public RentalDTO toDTO(Rental rental) {
        if (rental == null) {
            return null;
        }
        return new RentalDTO(
                rental.getId(),
                rental.getCustomerId(),
                rental.getCarId(),
                rental.getRentalPeriod().getStartDate(),
                rental.getRentalPeriod().getEndDate(),
                rental.getTotalCost().getAmount(),
                rental.getTotalCost().getCurrencyCode(),
                rental.getStatus().name(),
                rental.getCreatedAt()
        );
    }
}
