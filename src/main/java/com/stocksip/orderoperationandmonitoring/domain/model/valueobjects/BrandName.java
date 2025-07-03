package com.stocksip.orderoperationandmonitoring.domain.model.valueobjects;

public record BrandName(String value) {
    public BrandName {
        if (value == null || value.isBlank() || value.length() > 50)
            throw new IllegalArgumentException("BrandName is invalid");
    }

    @Override public String toString() { return value; }
}
