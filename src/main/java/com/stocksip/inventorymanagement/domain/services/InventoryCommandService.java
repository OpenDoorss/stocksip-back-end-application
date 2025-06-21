package com.stocksip.inventorymanagement.domain.services;

import com.stocksip.inventorymanagement.domain.model.commands.*;

/**
 * InventoryCommandService is an interface that defines methods for handling commands related to product inventories in a warehouse.
 *
 * @summary
 * Service interface for handling commands related to inventories.
 *
 * @since 1.0.0
 */
public interface InventoryCommandService {

    void handle(AddStockToProductCommand command);
    void handle(ReduceStockFromProductCommand command);
    void handle(AddProductsToWarehouseCommand command);

    Long handle(MoveProductToAnotherWarehouseCommand command);
    Long handle(DeleteProductFromWarehouseCommand command);
}
