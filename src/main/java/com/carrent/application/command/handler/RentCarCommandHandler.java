package com.carrent.application.command.handler;

import com.carrent.application.command.RentCarCommand;
import com.carrent.domain.model.aggregate.Car;
import com.carrent.domain.model.aggregate.Customer;
import com.carrent.domain.model.aggregate.Rental;
import com.carrent.domain.model.event.RentalCreatedEvent;
import com.carrent.domain.model.valueobject.Money;
import com.carrent.domain.model.valueobject.RentalPeriod;
import com.carrent.domain.repository.CarRepository;
import com.carrent.domain.repository.CustomerRepository;
import com.carrent.domain.repository.RentalRepository;
import com.carrent.domain.service.RentalCalculationService;
import com.carrent.domain.service.RentalValidationService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RentCarCommandHandler {

    private final RentalRepository rentalRepository;
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;
    private final RentalCalculationService calculationService;
    private final RentalValidationService validationService;
    private final ApplicationEventPublisher eventPublisher;

    public RentCarCommandHandler(
            RentalRepository rentalRepository,
            CarRepository carRepository,
            CustomerRepository customerRepository,
            RentalCalculationService calculationService,
            RentalValidationService validationService,
            ApplicationEventPublisher eventPublisher) {
        this.rentalRepository = rentalRepository;
        this.carRepository = carRepository;
        this.customerRepository = customerRepository;
        this.calculationService = calculationService;
        this.validationService = validationService;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public Long handle(RentCarCommand command) {
        Customer customer = customerRepository.findById(command.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found: " + command.getCustomerId()));
        
        Car car = carRepository.findById(command.getCarId())
                .orElseThrow(() -> new IllegalArgumentException("Car not found: " + command.getCarId()));

        RentalPeriod rentalPeriod = new RentalPeriod(command.getStartDate(), command.getEndDate());
        
        validationService.validateRental(customer, car, rentalPeriod);
        
        Money totalCost = calculationService.calculateRentalCost(rentalPeriod);
        
        Rental rental = new Rental(customer, car, rentalPeriod, totalCost);
        rental.activate();
        
        Rental savedRental = rentalRepository.save(rental);
        
        eventPublisher.publishEvent(new RentalCreatedEvent(
                savedRental.getId(),
                savedRental.getCustomer().getId(),
                savedRental.getCar().getId()
        ));
        
        return savedRental.getId();
    }
}
