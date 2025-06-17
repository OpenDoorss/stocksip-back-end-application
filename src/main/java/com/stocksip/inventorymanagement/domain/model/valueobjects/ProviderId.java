package com.stocksip.inventorymanagement.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

/**
 * Value Object representing a Provider ID which is the ID of another user.
 *
 * @summary
 * Represents a unique identifier for a provider.
 * This class is immutable and ensures that the provider ID is always a positive number.
 * @param providerId the unique identifier for the provider that must be a positive number.
 *
 * @see IllegalArgumentException
 *
 * @since 1.0.0
 */
@Embeddable
public record ProviderId(Long providerId) {

    public ProviderId {
        if (providerId == null || providerId <= 0) {
            throw new IllegalArgumentException("Provider ID must be a positive number.");
        }
    }
}
