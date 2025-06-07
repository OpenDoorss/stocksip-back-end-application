package com.stocksip.inventorymanagement.application.internal.commandservices;

import com.stocksip.inventorymanagement.domain.model.aggregates.Warehouse;
import com.stocksip.inventorymanagement.domain.model.commands.CreateWarehouseCommand;
import com.stocksip.inventorymanagement.domain.services.WarehousesCommandService;
import com.stocksip.inventorymanagement.infrastructure.persistence.jpa.WarehouseRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @name WarehousesCommandServiceImpl
 *
 * @summary
 * Implementation of the WarehousesCommandService interface for handling commands related to warehouses.
 *
 * @since 1.0.0
 */
@Service
public class WarehousesCommandServiceImpl implements WarehousesCommandService {

    /**
     * Repository for accessing warehouse data.
     */
    private final WarehouseRepository warehouseRepository;

    public WarehousesCommandServiceImpl(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    /**
     * Handles the command to create a new warehouse.
     *
     * @param command the command containing the details of the warehouse to be created
     * @return an Optional containing the created Warehouse if successful, or empty if not
     * @throws IllegalArgumentException if a warehouse with the same address already exists
     */
    @Override
    public Optional<Warehouse> handle(CreateWarehouseCommand command) {
        if (warehouseRepository.existsByAddressStreetAndAddressCityAndAddressPostalCode(command.address().street(), command.address().city(), command.address().postalCode()))
            throw new IllegalArgumentException("Warehouse with the same address already exists.");
        var warehouse = new Warehouse(command);
        var createdWarehouse = warehouseRepository.save(warehouse);
        return Optional.of(createdWarehouse);
    }
}
