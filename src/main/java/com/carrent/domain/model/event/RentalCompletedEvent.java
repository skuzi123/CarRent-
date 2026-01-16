package com.carrent.domain.model.event;

import lombok.Getter;

@Getter
public class RentalCompletedEvent extends DomainEvent {
    private final Long rentalId;
    private final Long carId;

    public RentalCompletedEvent(Long rentalId, Long carId) {
        super();
        this.rentalId = rentalId;
        this.carId = carId;
    }
}
