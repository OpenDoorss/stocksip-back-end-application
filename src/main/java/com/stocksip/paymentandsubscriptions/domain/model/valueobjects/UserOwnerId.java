package com.stocksip.paymentandsubscriptions.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record UserOwnerId(Long userId) {

    public UserOwnerId {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("User ID must be a positive number.");
        }
    }
}
