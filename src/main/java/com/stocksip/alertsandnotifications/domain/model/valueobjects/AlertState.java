package com.stocksip.alertsandnotifications.domain.model.valueobjects;

/**
 * This enum represents the different states an alert can be in.
 *
 * @summary
 * The states include:
 * - ACTIVE: The alert is currently active and requires attention.
 * - NOT_READ: The user has not read the alert.
 * - READ: The user has read the alert or notification.
 * - RESOLVED: The alert has been resolved or addressed.
 *
 * @since 1.0
 */
public enum AlertState {
    ACTIVE,
    NOT_READ,
    READ,
    RESOLVED
} 