package com.stocksip.inventorymanagement.domain.services;

import com.stocksip.inventorymanagement.domain.model.aggregates.Warehouse;
import com.stocksip.inventorymanagement.domain.model.queries.GetAllWarehousesByIdQuery;
import com.stocksip.inventorymanagement.domain.model.queries.GetWarehouseByIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for querying warehouses.
 *
 * @since 1.0.0
 */
public interface WarehouseQueryService {

    /**
     * Handles the query to retrieve a warehouse by its ID.
     *
     * @param query the query containing the warehouse ID
     * @return an Optional containing the Warehouse if found, or empty if not found
     */
    Optional<Warehouse> handle(GetWarehouseByIdQuery query);

    /**
     * Handles the query to retrieve all warehouses associated with a specific profile ID.
     *
     * @param query the query containing the profile ID
     * @return a List of Warehouses associated with the specified profile ID
     */
    List<Warehouse> handle(GetAllWarehousesByIdQuery query);
}
