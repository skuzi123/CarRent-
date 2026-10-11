package com.carrent.integration.event;

import lombok.Getter;

@Getter
public class RentalCompletedIntegrationEvent extends IntegrationEvent {
    private final Long rentalId;
    private final Long carId;

    public RentalCompletedIntegrationEvent(Long rentalId, Long carId) {
        this.rentalId = rentalId;
        this.carId = carId;
    }
}
