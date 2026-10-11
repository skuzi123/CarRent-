package com.carrent.integration.event;

import lombok.Getter;

@Getter
public class RentalCreatedIntegrationEvent extends IntegrationEvent {
    private final Long rentalId;
    private final Long customerId;
    private final Long carId;

    public RentalCreatedIntegrationEvent(Long rentalId, Long customerId, Long carId) {
        this.rentalId = rentalId;
        this.customerId = customerId;
        this.carId = carId;
    }
}
