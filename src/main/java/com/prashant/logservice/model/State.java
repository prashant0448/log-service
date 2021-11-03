package com.prashant.logservice.model;

import java.util.Arrays;

public enum State {

    STARTED("STARTED"),
    FINISHED("FINISHED");

    private final String state;

    State(String value) {
        this.state = value;
    }

    public String getState() {
        return state;
    }

    public static State fromValue(String message) {
        return Arrays.stream(values())
                .filter(s -> s.getState().equals(message))
                .findFirst()
                .orElse(null);
    }
}
