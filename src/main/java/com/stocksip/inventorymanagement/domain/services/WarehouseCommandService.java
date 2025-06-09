package com.stocksip.inventorymanagement.domain.services;

import com.stocksip.inventorymanagement.domain.model.aggregates.Warehouse;
import com.stocksip.inventorymanagement.domain.model.commands.CreateWarehouseCommand;
import com.stocksip.inventorymanagement.domain.model.commands.UpdateWarehouseCommand;

import java.util.Optional;

/**
 * WarehousesCommandService is an interface that defines methods for handling commands related to warehouses.
 *
 * @summary
 * Service interface for handling commands related to warehouses.
 *
 * @since 1.0.0
 */
public interface WarehouseCommandService {

    /**
     * Handles the creation of a new warehouse.
     *
     * @param command the command containing the details for creating a warehouse
     * @return an Optional containing the created Warehouse if successful, or empty if not
     * @see CreateWarehouseCommand
     */
    Optional<Warehouse> handle(CreateWarehouseCommand command);

    /**
     * Handles the update of an existing warehouse.
     *
     * @param command the command containing the details for updating a warehouse
     * @return an Optional containing the updated Warehouse if successful, or empty if not
     * @see UpdateWarehouseCommand
     */
    Optional<Warehouse> handle(UpdateWarehouseCommand command);
}
