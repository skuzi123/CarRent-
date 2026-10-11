package com.carrent.boundedcontext.rental.domain.model.event;

import lombok.Getter;

@Getter
public class RentalCancelledEvent extends DomainEvent {
    private final Long rentalId;

    public RentalCancelledEvent(Long rentalId) {
        super();
        this.rentalId = rentalId;
    }
}
