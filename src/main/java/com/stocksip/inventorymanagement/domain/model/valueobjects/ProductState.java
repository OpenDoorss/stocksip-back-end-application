package com.stocksip.inventorymanagement.domain.model.valueobjects;

/**
 * Enumeration representing the current state of a product.
 *
 * @summary
 * This enum defines the possible states of liquor. It is used to track the reason of the exit of a product.
 * The possible states  are:
 * - IN_WAREHOUSE: Default state for the product. It's when the product is still in the warehouse of the owner of the liquor store.
 * - BOUGHT: When the product was bought.
 * - DONATED: When the product was donated.
 * - LOST: When the product is lost because of the expiration date has passed.
 * - CONSUMED: When a product is consumed internally by the owner of the liquor store.
 *
 * @since 1.0.0
 */
public enum ProductState {
    IN_WAREHOUSE,
    BOUGHT,
    DONATED,
    LOST,
    CONSUMED
}
