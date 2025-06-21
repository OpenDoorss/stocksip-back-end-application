package com.stocksip.inventorymanagement.domain.services;

import com.stocksip.inventorymanagement.domain.model.aggregates.Inventory;
import com.stocksip.inventorymanagement.domain.model.queries.GetInventoryByProductIdAndWarehouseIdQuery;

import java.util.Optional;

/**
 * InventoryQueryService is an interface that defines methods for handling queries related to inventories.
 *
 * @summary
 * Service interface for handling queries related to inventories.
 *
 * @since 1.0.0
 */
public interface InventoryQueryService {

    Optional<Inventory> handle(GetInventoryByProductIdAndWarehouseIdQuery query);
}
