package com.stocksip.inventorymanagement.interfaces.rest.resources;

/**
 * UpdateProductResource is a record that represents an UpdateProductCommand resource in the REST API.
 *
 * @summary
 * This record encapsulates the details of an UpdateProductCommand.
 *
 * @since 1.0.0
 */
public record UpdateProductResource(
        double updatedUnitPriceAmount,
        int updatedMinimumStock,
        String updatedImageUrl
) {
}
