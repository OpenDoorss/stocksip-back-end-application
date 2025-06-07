package com.stocksip.inventorymanagement.application.internal.queryservices;

import com.stocksip.inventorymanagement.domain.services.WarehouseQueryService;
import com.stocksip.inventorymanagement.infrastructure.persistence.jpa.WarehouseRepository;
import org.springframework.stereotype.Service;

/**
 * This class implements the WarehouseQueryService interface to provide methods for querying warehouse data.
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
    private final WarehouseRepository warehouseRepository;

    /**
     * Dependency injection constructor for WarehouseQueryServiceImpl.
     * @param warehouseRepository the repository for accessing warehouse data
     */
    public WarehouseQueryServiceImpl(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }
}
