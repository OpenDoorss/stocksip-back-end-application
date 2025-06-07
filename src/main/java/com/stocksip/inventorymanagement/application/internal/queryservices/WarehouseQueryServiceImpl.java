package com.stocksip.inventorymanagement.application.internal.queryservices;

import com.stocksip.inventorymanagement.domain.model.aggregates.Warehouse;
import com.stocksip.inventorymanagement.domain.model.queries.GetWarehouseByStreetAndCityAndPostalCodeQuery;
import com.stocksip.inventorymanagement.domain.services.WarehouseQueryService;
import com.stocksip.inventorymanagement.infrastructure.persistence.jpa.WarehouseRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @name WarehouseQueryServiceImpl
 *
 * @summary
 * Implementation of the WarehouseQueryService interface for handling queries related to warehouses.
 *
 * @since 1.0.0
 */
@Service
public class WarehouseQueryServiceImpl implements WarehouseQueryService {

    /**
     * Repository for accessing warehouse data.
     */
    private WarehouseRepository warehouseRepository;

    /**
     * Dependency injection constructor for WarehouseQueryServiceImpl.
     * @param warehouseRepository the repository for accessing warehouse data
     */
    public WarehouseQueryServiceImpl(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    /**
     * Handles the query to find a warehouse by its street, city, and postal code.
     *
     * @param query the query containing the street, city, and postal code
     * @return an Optional containing the found Warehouse if it exists, or empty if not
     */
    @Override
    public Optional<Warehouse> handle(GetWarehouseByStreetAndCityAndPostalCodeQuery query) {
        return warehouseRepository.existByAddressByStreetAndCityAndPostalCode(query.street(), query.city(), query.postalCode());
    }
}
