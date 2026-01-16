package com.carrent.application.command.handler;

import com.carrent.application.command.CancelRentalCommand;
import com.carrent.domain.model.aggregate.Rental;
import com.carrent.domain.repository.RentalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CancelRentalCommandHandler {

    private final RentalRepository rentalRepository;

    public CancelRentalCommandHandler(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    @Transactional
    public void handle(CancelRentalCommand command) {
        Rental rental = rentalRepository.findById(command.getRentalId())
                .orElseThrow(() -> new IllegalArgumentException("Rental not found: " + command.getRentalId()));

        rental.cancel();
        rentalRepository.save(rental);
    }
}
