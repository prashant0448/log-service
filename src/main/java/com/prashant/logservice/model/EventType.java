package com.prashant.logservice.model;

import java.util.Arrays;

public enum EventType {

    APPLICATION_LOG("APPLICATION_LOG");

    private final String eventType;

    EventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventType() {
        return eventType;
    }

    public static EventType fromValue(String message) {
        return Arrays.stream(values())
                .filter(p -> p.getEventType().equals(message))
                .findFirst()
                .orElse(null);
    }
}
