package com.stocksip.orderoperationandmonitoring.domain.model.entities;

/**
 * Enumeration representing the status of an order.
 *
 * @summary
 * This enum defines various states an order can be in,
 * COMPLETED: The order has been successfully completed.
 * IN_PROGRESS: The order is currently being processed.
 * PENDING: The order is awaiting further action or confirmation.
 * ACCEPTED: The order has been accepted and will be processed.
 * CANCELLED: The order has been cancelled and will not be processed.
 *
 * @since 1.0
 */
public enum Status {
    COMPLETED,
    IN_PROGRESS,
    PENDING,
    ACCEPTED,
    CANCELLED,
}
