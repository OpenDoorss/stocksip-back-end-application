package com.stocksip.inventorymanagement.domain.model.commands;

/**
 * This command is used to delete a product only when it has 0 stock in all the warehouses.
 */
public record DeleteProductCommand(Long productId) {
}
