package com.stocksip.orderoperationandmonitoring.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record CatalogName(String value) {
    public CatalogName {
        if (value == null || value.isBlank() || value.length() < 3 || value.length() > 50)
            throw new IllegalArgumentException("CatalogName is too short");
    }
}