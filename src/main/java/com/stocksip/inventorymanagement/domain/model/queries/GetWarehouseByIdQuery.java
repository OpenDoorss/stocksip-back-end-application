package com.stocksip.inventorymanagement.domain.model.queries;

/**
 * Query to get a warehouse by its ID.
 *
 * @summary
 * This record represents a query to retrieve a warehouse based on its unique identifier.
 * It ensures that the provided warehouse ID is valid (not null and positive).
 *
 * @since 1.0.0
 */
public record GetWarehouseByIdQuery(Long warehouseId) {

    /**
     * Constructor for GetWarehouseByIdQuery.
     *
     * @param warehouseId the unique identifier of the warehouse to retrieve
     * @throws IllegalArgumentException if the warehouse ID is null or not positive
     *
     */
    public GetWarehouseByIdQuery {
        if (warehouseId == null || warehouseId <= 0) {
            throw new IllegalArgumentException("Warehouse ID must be a positive number.");
        }
    }
}
