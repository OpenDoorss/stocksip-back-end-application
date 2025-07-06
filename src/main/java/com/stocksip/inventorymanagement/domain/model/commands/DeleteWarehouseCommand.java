package com.stocksip.inventorymanagement.domain.model.commands;

/**
 * Command to delete a warehouse.
 * This command is used to request the deletion of a warehouse by its ID.
 * @param warehouseId The ID of the warehouse to be deleted.
 * @since 1.0.0
 */
public record DeleteWarehouseCommand(Long warehouseId) {
}
