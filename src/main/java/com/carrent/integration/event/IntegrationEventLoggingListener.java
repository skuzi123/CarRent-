package com.carrent.integration.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class IntegrationEventLoggingListener {
    private static final Logger log = LoggerFactory.getLogger(IntegrationEventLoggingListener.class);

    @EventListener
    public void onIntegrationEvent(IntegrationEvent event) {
        log.info("Integration event published: {} with id {}", event.getClass().getSimpleName(), event.getEventId());
    }
}
