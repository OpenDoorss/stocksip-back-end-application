package com.stocksip.alertsandnotifications.domain.model.valueobjects;

/**
 * This enum defines the different types of notifications that can be sent within the system.
 *
 * @summary
 * These notifications are used to inform users about various events related to products and purchase orders.
 * The values can be the next ones:
 * - PRODUCT_PRICE_CHANGE: Notification for changes in product prices.
 * - PRODUCT_PRICE_DISCOUNTED: Notification for discounted product prices. Only used for providers and its catalogs.
 * - PURCHASE_ORDER_CREATED: Notification for the creation of a new purchase order.
 * - PURCHASE_ORDER_APPROVED: Notification for the approval of a purchase order.
 * - PURCHASE_ORDER_REJECTED: Notification for the rejection of a purchase order.
 * - PURCHASE_ORDER_UPDATED: Notification for updates made to a purchase order.
 * - PURCHASE_ORDER_CANCELLED: Notification for the cancellation of a purchase order.
 * - PURCHASE_ORDER_COMPLETED: Notification for the completion of a purchase order.
 * - SALES_ORDER_RECEIVED: Notification sent to a provider when he receives a sales order.
 * - SALES_ORDER_REJECTED: Notification sent to a provider when he rejects a sales order.
 * - SALES_ORDER_UPDATED: Notification sent to a provider when he updates a sales order.
 * - SALES_ORDER_COMPLETED: Notification sent to a provider when he completes a sales order.
 * - SALES_ORDER_CANCELLED: Notification sent to a provider when he cancels a sales order.
 *
 * @since 1.0
 */
public enum NotificationTypes {
    PRODUCT_PRICE_CHANGE,
    PRODUCT_PRICE_DISCOUNTED,
    PURCHASE_ORDER_CREATED,
    PURCHASE_ORDER_APPROVED,
    PURCHASE_ORDER_REJECTED,
    PURCHASE_ORDER_UPDATED,
    PURCHASE_ORDER_CANCELLED,
    PURCHASE_ORDER_COMPLETED,
    SALES_ORDER_RECEIVED,
    SALES_ORDER_REJECTED,
    SALES_ORDER_UPDATED,
    SALES_ORDER_COMPLETED,
    SALES_ORDER_CANCELLED
} 