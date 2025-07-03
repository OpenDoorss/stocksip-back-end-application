package com.stocksip.orderoperationandmonitoring.domain.model.valueobjects;

public record ProductType(String value) {


    public ProductType {
        if (value == null || value.isBlank())
            throw new IllegalArgumentException("ProductType is blank");
    }

    @Override public String toString() { return value.toUpperCase(); }
}
