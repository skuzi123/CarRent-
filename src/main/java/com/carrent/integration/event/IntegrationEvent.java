package com.carrent.integration.event;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class IntegrationEvent {
    private final UUID eventId;
    private final LocalDateTime occurredOn;

    protected IntegrationEvent() {
        this.eventId = UUID.randomUUID();
        this.occurredOn = LocalDateTime.now();
    }

    public UUID getEventId() {
        return eventId;
    }

    public LocalDateTime getOccurredOn() {
        return occurredOn;
    }
}
