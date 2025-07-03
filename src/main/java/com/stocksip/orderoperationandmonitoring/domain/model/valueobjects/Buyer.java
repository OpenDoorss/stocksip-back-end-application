package com.stocksip.orderoperationandmonitoring.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record Buyer(
        Long accountId,
        Long userOwnerId,
        String role,
        String businessName,
        String email
) {
    public Buyer {
        if (accountId == null || userOwnerId == null || role == null || email == null) {
            throw new IllegalArgumentException("Buyer fields must not be null");
        }
    }
}
