package com.stocksip.paymentandsubscriptions.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record BusinessName(String value) {

    public BusinessName {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Business name cannot be blank");
        }
        if (value.length() > 100) {
            throw new IllegalArgumentException("Business name too long");
        }
    }

    public String value() {
        return value;
    }
}
