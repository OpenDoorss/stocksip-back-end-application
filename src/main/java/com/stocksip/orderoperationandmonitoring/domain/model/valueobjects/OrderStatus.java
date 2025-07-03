package com.stocksip.orderoperationandmonitoring.domain.model.valueobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum OrderStatus {
    RECEIVED,
    IN_PROCESS,
    ARRIVED,
    CANCELED;

    @JsonCreator
    public static OrderStatus from(String raw) {
        if (raw == null) throw new IllegalArgumentException("Status null");
        return switch (raw.trim().toUpperCase().replace(' ', '_')) {
            case "RECEIVED"   -> RECEIVED;
            case "IN_PROCESS" -> IN_PROCESS;
            case "ARRIVED"    -> ARRIVED;
            case "CANCELED"   -> CANCELED;
            default -> throw new IllegalArgumentException("Bad status: " + raw);
        };
    }

    @JsonValue
    public String value() {
        return name();
    }
}

