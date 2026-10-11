package com.carrent.boundedcontext.rental.application.command.handler;

import com.carrent.boundedcontext.rental.application.command.RentCarCommand;
import com.carrent.boundedcontext.rental.domain.model.aggregate.Rental;
import com.carrent.boundedcontext.rental.domain.model.event.RentalCreatedEvent;
import com.carrent.boundedcontext.rental.domain.model.valueobject.Money;
import com.carrent.boundedcontext.rental.domain.model.valueobject.RentalPeriod;
import com.carrent.boundedcontext.rental.domain.repository.RentalRepository;
import com.carrent.boundedcontext.rental.domain.service.RentalCalculationService;
import com.carrent.boundedcontext.rental.domain.service.RentalValidationService;
import com.carrent.integration.event.RentalCreatedIntegrationEvent;
import com.carrent.integration.publisher.IntegrationEventPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RentCarCommandHandler {

    private final RentalRepository rentalRepository;
    private final RentalCalculationService calculationService;
    private final RentalValidationService validationService;
    private final ApplicationEventPublisher eventPublisher;
    private final IntegrationEventPublisher integrationEventPublisher;

    public RentCarCommandHandler(
            RentalRepository rentalRepository,
            RentalCalculationService calculationService,
            RentalValidationService validationService,
            ApplicationEventPublisher eventPublisher,
            IntegrationEventPublisher integrationEventPublisher) {
        this.rentalRepository = rentalRepository;
        this.calculationService = calculationService;
        this.validationService = validationService;
        this.eventPublisher = eventPublisher;
        this.integrationEventPublisher = integrationEventPublisher;
    }

    @Transactional
    public Long handle(RentCarCommand command) {
        var rentalPeriod = new RentalPeriod(command.getStartDate(), command.getEndDate());
        
        validationService.validateRental(command.getCustomerId(), command.getCarId(), rentalPeriod);
        
        Money totalCost = calculationService.calculateRentalCost(rentalPeriod);
        
        var rental = new Rental(command.getCustomerId(), command.getCarId(), rentalPeriod, totalCost);
        rental.activate();
        
        Rental savedRental = rentalRepository.save(rental);

        eventPublisher.publishEvent(new RentalCreatedEvent(
                savedRental.getId(),
                savedRental.getCustomerId(),
                savedRental.getCarId()
        ));

        integrationEventPublisher.publish(new RentalCreatedIntegrationEvent(
                savedRental.getId(),
                savedRental.getCustomerId(),
                savedRental.getCarId()
        ));
        
        return savedRental.getId();
    }
}
