package com.stocksip.inventorymanagement.interfaces.rest.resources;

/**
 * ProductResource is a record that represents a product resource in the REST API.
 *
 * @summary
 * This record encapsulates the details of a product.
 *
 * @since 1.0.0
 */
public record ProductResource(
        Long id,
        String imageUrl,
        String name,
        String brandName,
        String liquorType,
        double unitPriceAmount,
        int minimumStock,
        Long accountId) {
}
