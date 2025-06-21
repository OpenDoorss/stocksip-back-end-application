package com.stocksip.inventorymanagement.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

/**
 * Value Object representing an Account ID.
 *
 * @summary
 * Represents a unique identifier for an account.
 * This class is immutable and ensures that the account ID is always a positive number.
 * @param accountId the unique identifier for the account that must be a positive number.
 *
 * @see IllegalArgumentException
 *
 * @since 1.0.0
 */
@Embeddable
public record AccountId(Long accountId) {

    /**
     * Constructs an AccountId with the given ID.
     *
     * @param accountId the unique identifier for the account. Must be a positive number.
     * @throws IllegalArgumentException if the accountId is null or not positive.
     */
    public AccountId {
        if (accountId == null || accountId <= 0) {
            throw new IllegalArgumentException("Profile ID must be a positive number.");
        }
    }

    /**
     * Factory method to create a AccountId from a Long value.
     *
     * @param id the unique identifier for the account. Must be a positive number.
     * @return a new AccountId instance.
     * @throws IllegalArgumentException if the id is null or not positive.
     */
    public static AccountId from(Long id) {
        return new AccountId(id);
    }
}
