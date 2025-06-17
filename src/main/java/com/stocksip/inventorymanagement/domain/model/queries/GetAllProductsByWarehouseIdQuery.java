package com.stocksip.inventorymanagement.domain.model.queries;

/**
 * GetAllProductsByWarehouseIdQuery
 *
 * @summary
 * GetAllProductsByWarehouseIdQuery is a query object used to retrieve all products associated with a specific warehouse.
 *
 * @param warehouseId The ID of the warehouse for which products are to be retrieved.
 */
public record GetAllProductsByWarehouseIdQuery(Long warehouseId) {

    /**
     * Validates the warehouse ID of the query.
     */
    public GetAllProductsByWarehouseIdQuery {
        if (warehouseId == null || warehouseId <= 0) {
            throw new IllegalArgumentException("Warehouse ID must be a positive number.");
        }
    }
}
