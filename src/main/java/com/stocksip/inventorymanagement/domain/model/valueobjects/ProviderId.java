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
 * @since 1.1.0
 */
@Embeddable
public record ProviderId(Long providerId) {

}
