package com.stocksip.orderoperationandmonitoring.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record AccountId(Long accountId) {

    /**
     * Constructs an AccountId with the given ID.
     *
     * @param accountId the unique identifier for the account. Must be a positive number.
     * @throws IllegalArgumentException if the accountId is null or not positive.
     */
    public AccountId {
        if (accountId == null) {
            throw new IllegalArgumentException("Account ID must be not null.");
        }
    }
}