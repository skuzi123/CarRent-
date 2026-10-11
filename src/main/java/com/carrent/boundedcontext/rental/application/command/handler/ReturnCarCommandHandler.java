package com.carrent.boundedcontext.rental.application.command.handler;

import com.carrent.boundedcontext.rental.application.command.ReturnCarCommand;
import com.carrent.boundedcontext.rental.domain.model.aggregate.Rental;
import com.carrent.boundedcontext.rental.domain.model.event.RentalCompletedEvent;
import com.carrent.boundedcontext.rental.domain.repository.RentalRepository;
import com.carrent.integration.event.RentalCompletedIntegrationEvent;
import com.carrent.integration.publisher.IntegrationEventPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReturnCarCommandHandler {

    private final RentalRepository rentalRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final IntegrationEventPublisher integrationEventPublisher;

    public ReturnCarCommandHandler(
            RentalRepository rentalRepository,
            ApplicationEventPublisher eventPublisher,
            IntegrationEventPublisher integrationEventPublisher) {
        this.rentalRepository = rentalRepository;
        this.eventPublisher = eventPublisher;
        this.integrationEventPublisher = integrationEventPublisher;
    }

    @Transactional
    public void handle(ReturnCarCommand command) {
        Rental rental = rentalRepository.findById(command.getRentalId())
                .orElseThrow(() -> new IllegalArgumentException("Rental not found: " + command.getRentalId()));

        rental.complete();
        rentalRepository.save(rental);

        eventPublisher.publishEvent(new RentalCompletedEvent(
                rental.getId(),
                rental.getCarId()
        ));
        integrationEventPublisher.publish(new RentalCompletedIntegrationEvent(
                rental.getId(),
                rental.getCarId()
        ));
    }
}
