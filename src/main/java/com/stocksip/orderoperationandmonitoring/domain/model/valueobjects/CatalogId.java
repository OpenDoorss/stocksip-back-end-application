package com.stocksip.orderoperationandmonitoring.domain.model.valueobjects;

public record CatalogId(Long value) {
    public CatalogId {
        if (value == null || value <= 0)
            throw new IllegalArgumentException("CatalogId must be positive");
    }

    @Override public String toString() { return value.toString(); }
}