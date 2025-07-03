package com.stocksip.inventorymanagement.interfaces.rest.resources;

/**
 * CreateProductResource is a record that represents an CreateProductCommand resource in the REST API.
 *
 * @summary
 * This record encapsulates the details of an CreateProductCommand.
 *
 * @since 1.0.0
 */
public record CreateProductResource(
        String additionalName,
        String liquorType,
        String brandName,
        double unitPriceAmount,
        int minimumStock,
        String imageUrl,
        Long providerId
) {
}
