package com.stocksip.inventorymanagement.domain.services;

import com.stocksip.inventorymanagement.domain.model.aggregates.Inventory;
import com.stocksip.inventorymanagement.domain.model.queries.GetInventoryByIdQuery;
import com.stocksip.inventorymanagement.domain.model.queries.GetInventoryByProductIdAndWarehouseIdAndBestBeforeDateQuery;
import com.stocksip.inventorymanagement.domain.model.queries.GetInventoryByProductIdAndWarehouseIdQuery;

import java.util.List;
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

    Optional<Inventory> handle(GetInventoryByIdQuery query);
    Optional<Inventory> handle(GetInventoryByProductIdAndWarehouseIdAndBestBeforeDateQuery query);

    List<Inventory> handle(GetInventoryByProductIdAndWarehouseIdQuery query);
}
