package com.stocksip.inventorymanagement.domain.model.valueobjects;

/**
 * Enumeration representing the current state of a product.
 *
 * @summary
 * This enum defines the possible states of liquor.
 * The possible states are:
 * - WITH_STOCK: When the product has enough stocks to do the normal operations with it.
 * - OUT_OF_STOCK: When the product does not have a stock or is equal to 0.
 *
 * @since 1.0.1
 */
public enum ProductState {
    Out_Of_Stock,
    With_Stock
}
