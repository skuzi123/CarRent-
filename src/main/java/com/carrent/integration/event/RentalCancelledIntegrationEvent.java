package com.carrent.integration.event;

import lombok.Getter;

@Getter
public class RentalCancelledIntegrationEvent extends IntegrationEvent {
    private final Long rentalId;
    private final Long carId;

    public RentalCancelledIntegrationEvent(Long rentalId, Long carId) {
        this.rentalId = rentalId;
        this.carId = carId;
    }
}
