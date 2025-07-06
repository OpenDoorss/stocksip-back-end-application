package com.stocksip.inventorymanagement.domain.model.queries;

import java.util.Date;

/**
 * GetProductByIdAndWarehouseIdAndBestBeforeDateQuery
 *
 * @summary
 * GetProductByIdAndWarehouseIdAndBestBeforeDateQuery is a query that is used to retrieve a specific stock of products inside a warehouse with a specific best before date.
 *
 * @param productId The unique identifier of the product that will be retrieved.
 * @param warehouseId The unique identifier of the warehouse that store the stock of products.
 * @param bestBeforeDate The best before date of the stock of products to retrieve.
 */
public record GetProductByIdAndWarehouseIdAndBestBeforeDateQuery(Long productId, Long warehouseId, Date bestBeforeDate) {

    /**
     * Validates the command parameters.
     */
    public GetProductByIdAndWarehouseIdAndBestBeforeDateQuery {
        if (productId == null) {
            throw new IllegalArgumentException("Product Id cannot be null.");
        }
        if (warehouseId == null) {
            throw new IllegalArgumentException("Warehouse Id cannot be null.");
        }
    }
}
