package com.stocksip.profilemanagement.domain.model.valueobjects;

public record BusinessName(String businessName) {

    public BusinessName() {
        this(null);
    }

    public BusinessName(String businessName) {
        if (businessName == null || businessName.isBlank()) {
            throw new IllegalArgumentException("Business name cannot be null or blank");
        }
        this.businessName = businessName;
    }

}
