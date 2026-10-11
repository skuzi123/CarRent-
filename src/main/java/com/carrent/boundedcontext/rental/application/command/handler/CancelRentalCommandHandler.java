package com.carrent.boundedcontext.rental.application.command.handler;

import com.carrent.boundedcontext.rental.application.command.CancelRentalCommand;
import com.carrent.boundedcontext.rental.domain.model.aggregate.Rental;
import com.carrent.boundedcontext.rental.domain.model.event.RentalCancelledEvent;
import com.carrent.boundedcontext.rental.domain.repository.RentalRepository;
import com.carrent.integration.event.RentalCancelledIntegrationEvent;
import com.carrent.integration.publisher.IntegrationEventPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CancelRentalCommandHandler {

    private final RentalRepository rentalRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final IntegrationEventPublisher integrationEventPublisher;

    public CancelRentalCommandHandler(
            RentalRepository rentalRepository,
            ApplicationEventPublisher eventPublisher,
            IntegrationEventPublisher integrationEventPublisher) {
        this.rentalRepository = rentalRepository;
        this.eventPublisher = eventPublisher;
        this.integrationEventPublisher = integrationEventPublisher;
    }

    @Transactional
    public void handle(CancelRentalCommand command) {
        Rental rental = rentalRepository.findById(command.getRentalId())
                .orElseThrow(() -> new IllegalArgumentException("Rental not found: " + command.getRentalId()));

        rental.cancel();
        rentalRepository.save(rental);
        eventPublisher.publishEvent(new RentalCancelledEvent(rental.getId()));
        integrationEventPublisher.publish(new RentalCancelledIntegrationEvent(
                rental.getId(),
                rental.getCarId()
        ));
    }
}
