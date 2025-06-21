package com.stocksip.inventorymanagement.domain.model.queries;

/**
 * GetInventoryByProductIdAndWarehouseIdQuery
 *
 * @summary
 * GetInventoryByProductIdAndWarehouseIdQuery is a query used to get the stock details of a product in a warehouse.
 *
 * @param productId The unique identifier of the product inventory.
 * @param warehouseId The unique identifier of the warehouse that stores the product inventory.
 */
public record GetInventoryByProductIdAndWarehouseIdQuery(String productId, String warehouseId) {

}
