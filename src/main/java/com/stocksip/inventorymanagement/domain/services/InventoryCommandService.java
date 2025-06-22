package com.stocksip.inventorymanagement.domain.services;

import com.stocksip.inventorymanagement.domain.model.aggregates.Inventory;
import com.stocksip.inventorymanagement.domain.model.commands.*;

import java.util.Optional;

/**
 * InventoryCommandService is an interface that defines methods for handling commands related to product inventories in a warehouse.
 *
 * @summary
 * Service interface for handling commands related to inventories.
 *
 * @since 1.0.0
 */
public interface InventoryCommandService {

    Optional<Inventory> handle(AddStockToProductCommand command);
    Optional<Inventory> handle(ReduceStockFromProductCommand command);
    Optional<Inventory> handle(AddProductsToWarehouseCommand command);
    Optional<Inventory> handle(MoveProductToAnotherWarehouseCommand command);

    Long handle(DeleteProductFromWarehouseCommand command);
}
