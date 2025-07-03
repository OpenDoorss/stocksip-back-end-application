package com.stocksip.orderoperationandmonitoring.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

import java.time.LocalDateTime;
@Embeddable
public record DateCreated(LocalDateTime value) {
    public DateCreated {
        if (value == null) throw new IllegalArgumentException("DateCreated is null");
        if (value.isAfter(LocalDateTime.now()))
            throw new IllegalArgumentException("DateCreated cannot be in the future");
    }

    @Override public String toString() { return value.toString(); }
}
