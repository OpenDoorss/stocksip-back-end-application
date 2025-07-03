package com.stocksip.inventorymanagement.interfaces.rest.resources;

/**
 * UpdateProductMinimumStockResource is a record that represents an UpdateProductMinimumStockCommand resource in the REST API.
 *
 * @summary
 * This record encapsulates the details of an UpdateProductMinimumStockCommand.
 *
 * @since 1.0.0
 */
public record UpdateProductMinimumStockResource(
        int updatedMinimumStock
) {
}
