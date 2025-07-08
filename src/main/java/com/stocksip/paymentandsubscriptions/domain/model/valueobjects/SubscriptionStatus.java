package com.stocksip.paymentandsubscriptions.domain.model.valueobjects;

/**
 * Enum representing the status of a subscription.
 * <p>
 * PENDING: The subscription is created but not yet completed.
 * COMPLETED: The subscription has been successfully processed and is active.
 */
public enum SubscriptionStatus {
    PENDING,
    COMPLETED
}
