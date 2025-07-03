package com.stocksip.inventorymanagement.domain.services;

import com.stocksip.inventorymanagement.domain.model.aggregates.Warehouse;
import com.stocksip.inventorymanagement.domain.model.queries.GetAllWarehousesByAccountIdQuery;
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
     * Handles the query to retrieve all warehouses associated with a specific account ID.
     *
     * @param query the query containing the account ID
     * @return a List of Warehouses associated with the specified account ID
     */
    List<Warehouse> handle(GetAllWarehousesByIdQuery query);

    /**
     * Handles the query to retrieve all warehouses associated with a specific account ID.
     *
     * @param query The query containing the account ID.
     * @return A List of Warehouses associated with the specific account ID.
     */
    List<Warehouse> handle(GetAllWarehousesByAccountIdQuery query);
}
