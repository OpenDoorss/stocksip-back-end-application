package com.stocksip.inventorymanagement.domain.model.queries;

/**
 * Query to retrieve all product exits associated with a specific warehouse.
 * This query is used to fetch product exit records based on the warehouse ID.
 */
public record GetAllProductExitsByWarehouseIdQuery(Long warehouseId) {

    /**
     * Constructs a new GetAllProductExitsByWarehouseIdQuery.
     *
     * @param warehouseId the ID of the warehouse for which to retrieve product exits.
     * @throws IllegalArgumentException if the warehouseId is null or not positive.
     */
    public GetAllProductExitsByWarehouseIdQuery {
        if (warehouseId == null || warehouseId <= 0) {
            throw new IllegalArgumentException("Warehouse ID must be a positive number.");
        }
    }
}
