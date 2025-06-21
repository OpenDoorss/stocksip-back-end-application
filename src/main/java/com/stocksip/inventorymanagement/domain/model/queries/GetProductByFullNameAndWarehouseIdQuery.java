package com.stocksip.inventorymanagement.domain.model.queries;

/**
 * GetProductByFullNameAndWarehouseIdQuery
 *
 * @summary
 * GetProductByFullNameAndWarehouseIdQuery is a query used to retrieve a product by its full name in a specific warehouse..
 *
 * @param warehouseId The unique identifier of the warehouse that stores the product.
 * @param fullName The full name of the product that will be used to retrieve a product with that name.
 */
public record GetProductByFullNameAndWarehouseIdQuery(Long warehouseId, String fullName) {

    /**
     * Validates the command parameters
     */
    public GetProductByFullNameAndWarehouseIdQuery {
        if (warehouseId == null) {
            throw new IllegalArgumentException("Warehouse Id cannot be empty.");
        }
    }
}
