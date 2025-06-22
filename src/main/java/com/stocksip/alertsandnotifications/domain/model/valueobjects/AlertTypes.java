package com.stocksip.alertsandnotifications.domain.model.valueobjects;

/**
 * This enum represents the different types of alerts that can be generated in the system.
 *
 * @summary
 * The alerts are related to product stock levels and expiration status.
 * The possible values are:
 * - PRODUCT_LOW_STOCK: Alert for products that are running low on stock with an alert type of warning.
 * - PRODUCT_OUT_OF_STOCK: Alert for products that are completely out of stock with an alert type of critical.
 * - PRODUCT_ABOUT_TO_EXPIRE: Alert for products that are nearing their expiration date with an alert type of warning.
 * - PRODUCT_EXPIRED: Alert for products that have already expired with an alert type of critical.
 *
 * @since 1.0
 */
public enum AlertTypes {
    PRODUCT_LOW_STOCK,
    PRODUCT_OUT_OF_STOCK,
    PRODUCT_ABOUT_TO_EXPIRE,
    PRODUCT_EXPIRED
} 