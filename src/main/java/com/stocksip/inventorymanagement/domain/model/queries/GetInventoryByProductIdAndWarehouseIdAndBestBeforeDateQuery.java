package com.stocksip.inventorymanagement.domain.model.queries;

import java.util.Date;

/**
 * GetInventoryByProductIdAndWarehouseIdAndBestBeforeDateQuery
 *
 * @summary
 * GetInventoryByProductIdAndWarehouseIdAndBestBeforeDateQuery is a query used to retrieve an inventory entry by is product and warehouse ID and the best before date of the product stock.
 *
 * @param productId The unique identifier of the product inventory.
 * @param warehouseId The unique identifier of the warehouse that stores the product inventory.
 * @param bestBeforeDate The best before date.
 */
public record GetInventoryByProductIdAndWarehouseIdAndBestBeforeDateQuery(Long productId, Long warehouseId, Date bestBeforeDate) {
}
