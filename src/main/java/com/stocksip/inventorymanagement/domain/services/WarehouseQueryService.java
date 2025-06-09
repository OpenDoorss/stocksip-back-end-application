package com.stocksip.inventorymanagement.domain.services;

import com.stocksip.inventorymanagement.domain.model.aggregates.Warehouse;
import com.stocksip.inventorymanagement.domain.model.queries.GetWarehouseByIdQuery;

import java.util.Optional;

/**
 * Service interface for querying warehouses.
 *
 * @since 1.0.0
 */
public interface WarehouseQueryService {

    Optional<Warehouse> handle(GetWarehouseByIdQuery query);
}
