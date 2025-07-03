package com.stocksip.inventorymanagement.domain.model.queries;

/**
 * This query will retrieve a specific inventory by its id.
 * @param inventoryId The unique identifier of the inventory to retrieve.
 */
public record GetInventoryByIdQuery(Long inventoryId) {
}
