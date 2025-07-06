package com.stocksip.orderoperationandmonitoring.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

import java.time.LocalDateTime;

@Embeddable
public record OrderDate(LocalDateTime value) {

    public OrderDate {
        if (value == null) {
            throw new IllegalArgumentException("Order date must not be null");
        }
    }

    @Override public String toString() { return value.toString(); }
}

