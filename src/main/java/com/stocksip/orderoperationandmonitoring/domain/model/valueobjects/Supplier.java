package com.stocksip.orderoperationandmonitoring.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record Supplier(
        Long accountId,
        Long userOwnerId,
        String role,
        String businessName,
        String email
) {
    public Supplier {
        if (accountId == null || userOwnerId == null || role == null || email == null) {
            throw new IllegalArgumentException("Supplier fields must not be null");
        }
    }
}
