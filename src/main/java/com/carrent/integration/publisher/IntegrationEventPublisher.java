package com.carrent.integration.publisher;

import com.carrent.integration.event.IntegrationEvent;

public interface IntegrationEventPublisher {
    void publish(IntegrationEvent event);
}
