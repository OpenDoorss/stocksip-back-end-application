package com.stocksip.orderoperationandmonitoring.domain.model.valueobjects;

public record ProductName(String value) {
    public ProductName {
        if (value == null || value.isBlank() || value.length() > 60)
            throw new IllegalArgumentException("ProductName is invalid");
    }

    @Override public String toString() { return value; }
}