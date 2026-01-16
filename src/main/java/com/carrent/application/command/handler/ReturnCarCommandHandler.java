package com.carrent.application.command.handler;

import com.carrent.application.command.ReturnCarCommand;
import com.carrent.domain.model.aggregate.Rental;
import com.carrent.domain.model.event.RentalCompletedEvent;
import com.carrent.domain.repository.RentalRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReturnCarCommandHandler {

    private final RentalRepository rentalRepository;
    private final ApplicationEventPublisher eventPublisher;

    public ReturnCarCommandHandler(
            RentalRepository rentalRepository,
            ApplicationEventPublisher eventPublisher) {
        this.rentalRepository = rentalRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public void handle(ReturnCarCommand command) {
        Rental rental = rentalRepository.findById(command.getRentalId())
                .orElseThrow(() -> new IllegalArgumentException("Rental not found: " + command.getRentalId()));

        rental.complete();
        
        if (command.getNewMileage() != null) {
            rental.getCar().returnCar(command.getNewMileage());
        } else {
            rental.getCar().returnCar(null);
        }
        
        rentalRepository.save(rental);
        
        eventPublisher.publishEvent(new RentalCompletedEvent(
                rental.getId(),
                rental.getCar().getId()
        ));
    }
}
