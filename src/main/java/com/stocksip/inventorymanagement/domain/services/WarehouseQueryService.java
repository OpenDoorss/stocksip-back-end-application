package com.stocksip.inventorymanagement.domain.services;

import com.stocksip.inventorymanagement.domain.model.aggregates.Warehouse;
import com.stocksip.inventorymanagement.domain.model.queries.GetWarehouseByStreetAndCityAndPostalCodeQuery;

import java.util.Optional;

/**
 * Service interface for querying warehouses.
 * This service provides methods to retrieve warehouse information based on specific criteria.
 *
 * @since 1.0.0
 */
public interface WarehouseQueryService {

    /**
     * Retrieves a warehouse based on its street, city, and postal code.
     *
     * @param query the query containing the street, city, and postal code to search for
     * @return an Optional containing the found Warehouse if it exists, or empty if not found
     * @see GetWarehouseByStreetAndCityAndPostalCodeQuery
     */
    Optional<Warehouse> handle(GetWarehouseByStreetAndCityAndPostalCodeQuery query);
}
