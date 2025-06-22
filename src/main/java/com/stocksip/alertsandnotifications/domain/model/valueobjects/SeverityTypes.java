package com.stocksip.alertsandnotifications.domain.model.valueobjects;

/**
 * This enum represents the severity types for an alert.
 *
 * @summary
 * The possible values are:
 * - CRITICAL: Indicates a critical alert that requires immediate attention.
 * - WARNING: Indicates a warning alert that should be addressed but is not immediately critical.
 * - INFO: Indicates an informational alert that does not require immediate action but is useful to know. 
 *         If the alert is a type of this, then it transforms itself into a notification.
 *
 * @since 1.0
 */
public enum SeverityTypes {
    CRITICAL,
    WARNING,
    INFO
} 