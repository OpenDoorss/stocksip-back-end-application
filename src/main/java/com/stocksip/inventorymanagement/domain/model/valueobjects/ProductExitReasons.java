package com.stocksip.inventorymanagement.domain.model.valueobjects;

/**
 * Enumeration representing the reason of the exit a product.
 *
 * @summary
 * This enum defines the possible reasons of the exit of a product. It is used to track the reason of the exit of a product.
 * The possible states are:
 * - SOLD: When the product is sold.
 * - DONATED: When the product is donated.
 * - EXPIRED: When the product is lost because of the expiration date has passed.
 * - SPOILED: When the product is lost because of the conditions it was stored.
 * - DAMAGED: When the product packaging is damaged and can no longer be sold.
 * - BROKE: When the product fell and broke.
 * - CONSUMED: When a product is consumed internally by the owner of the liquor store.
 *
 * @since 1.0.0
 */
public enum ProductExitReasons {
    SOLD,
    DONATED,
    EXPIRED,
    SPOILED,
    DAMAGED,
    BROKE,
    CONSUMED
}
