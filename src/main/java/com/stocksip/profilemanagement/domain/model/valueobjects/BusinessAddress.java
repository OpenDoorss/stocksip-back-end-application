package com.stocksip.profilemanagement.domain.model.valueobjects;

public record BusinessAddress(String address) {
    public BusinessAddress() {
        this(null);
    }

    public BusinessAddress(String address) {
        if (address == null || address.isBlank()) {
            throw new IllegalArgumentException("Business address cannot be null or blank");
        }
        this.address = address;
    }
}
