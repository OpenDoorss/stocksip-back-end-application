package com.stocksip.inventorymanagement.domain.model.queries;

import java.util.Optional;

/**
 * GetProductByFullNameAndWarehouseIdQuery
 *
 * @summary
 * GetProductByFullNameAndWarehouseIdQuery is a query used to retrieve a product by its full name in a specific warehouse..
 *
 * @param warehouseId The unique identifier of the warehouse that stores the product.
 */
public record GetProductByFullNameAndWarehouseIdQuery(Long warehouseId, String additionalName, String liquorType, String brandName) {

    /**
     * Validates the command parameters
     */
    public GetProductByFullNameAndWarehouseIdQuery {
        if (warehouseId == null) {
            throw new IllegalArgumentException("Warehouse Id cannot be empty.");
        }
    }
}
