package com.stocksip.inventorymanagement.domain.model.queries;

/**
 * GetProductsByIdAndWarehouseIdQuery
 *
 * @summary
 * GetProductsByIdAndWarehouseIdQuery is a query used to retrieve a stock or stocks of products in a specific warehouse
 *
 * @param productId The unique identifier of the stock or stocks of product that will be retrieved.
 * @param warehouseId The unique identifier of the warehouse that store the stock of products.
 */
public record GetProductsByIdAndWarehouseIdQuery(Long productId, Long warehouseId) {

    public GetProductsByIdAndWarehouseIdQuery {
        if (productId == null) {
            throw new IllegalArgumentException("Product Id cannot be null.");
        }
        if (warehouseId == null) {
            throw new IllegalArgumentException("Warehouse Id cannot be null.");
        }
    }
}
