package com.carrent.boundedcontext.rental.domain.model.event;

import lombok.Getter;

@Getter
public class RentalCreatedEvent extends DomainEvent {
    private final Long rentalId;
    private final Long customerId;
    private final Long carId;

    public RentalCreatedEvent(Long rentalId, Long customerId, Long carId) {
        super();
        this.rentalId = rentalId;
        this.customerId = customerId;
        this.carId = carId;
    }
}
