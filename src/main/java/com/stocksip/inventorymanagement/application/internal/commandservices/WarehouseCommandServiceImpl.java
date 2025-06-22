package com.stocksip.inventorymanagement.application.internal.commandservices;

import com.stocksip.inventorymanagement.domain.model.aggregates.Warehouse;
import com.stocksip.inventorymanagement.domain.model.commands.CreateWarehouseCommand;
import com.stocksip.inventorymanagement.domain.model.commands.DeleteWarehouseCommand;
import com.stocksip.inventorymanagement.domain.model.commands.UpdateWarehouseCommand;
import com.stocksip.inventorymanagement.domain.model.valueobjects.AccountId;
import com.stocksip.inventorymanagement.domain.services.WarehouseCommandService;
import com.stocksip.inventorymanagement.infrastructure.persistence.jpa.repositories.WarehouseRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * WarehousesCommandServiceImpl is an implementation of the WarehousesCommandService interface.
 *
 * @summary
 * Implementation of the WarehousesCommandService interface for handling commands related to warehouses.
 *
 * @since 1.0.0
 */
@Service
public class WarehouseCommandServiceImpl implements WarehouseCommandService {

    /**
     * Repository for accessing warehouse data.
     */
    private final WarehouseRepository warehouseRepository;

    public WarehouseCommandServiceImpl(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    /**
     * Handles the command to create a new warehouse.
     *
     * @param command the command containing the details of the warehouse to be created
     * @return an Optional containing the created Warehouse if successful, or empty if not
     * @throws IllegalArgumentException if a warehouse with the same name or address already exists
     */
    @Override
    public Optional<Warehouse> handle(CreateWarehouseCommand command) {
        if (warehouseRepository.existsByNameIgnoreCaseAndAccountId(command.name(), new AccountId(command.accountId())))
            throw new IllegalArgumentException("Warehouse with the same name already exists.");

        if (warehouseRepository.existsByAddressStreetAndAddressCityAndAddressPostalCodeIgnoreCaseAndAccountId(
                command.street(),
                command.city(),
                command.postalCode(),
                new AccountId(command.accountId()))) {
            throw new IllegalArgumentException("Warehouse with the same address already exists.");
        }

        var warehouse = new Warehouse(command);
        var createdWarehouse = warehouseRepository.save(warehouse);
        return Optional.of(createdWarehouse);
    }

    /**
     * Handles the command to update an existing warehouse.
     *
     * @param command the command containing the updated details of the warehouse
     * @return an Optional containing the updated Warehouse if successful, or empty if not
     * @throws IllegalArgumentException if the warehouse does not exist, or if a warehouse with the same name or address already exists
     */
    @Override
    public Optional<Warehouse> handle(UpdateWarehouseCommand command) {

        var warehouseToUpdate = warehouseRepository.findWarehouseByWarehouseIdAndAccountId(command.warehouseId(), new AccountId(command.accountId()))
                .orElseThrow(() -> new IllegalArgumentException("Warehouse with ID %s does not exist".formatted(command.warehouseId())));

        if (!warehouseToUpdate.getName().equals(command.name()) &&
                warehouseRepository.existsByNameAndAccountIdAndWarehouseIdIsNot(command.name(), new AccountId(command.accountId()), command.warehouseId())) {
            throw new IllegalArgumentException("Warehouse with name %s already exists".formatted(command.name()));
        }

        boolean isAddressChanged = !warehouseToUpdate.getAddress().fullAddress().equals(
                String.format("%s, %s, %s, %s, %s",
                        command.street(),
                        command.city(),
                        command.district(),
                        command.postalCode(),
                        command.country()));

        if (isAddressChanged &&
                warehouseRepository.existsByAddressStreetIgnoreCaseAndAddressCityIgnoreCaseAndAddressPostalCodeIgnoreCaseAndAccountIdAndWarehouseIdIsNot(
                        command.street(),
                        command.city(),
                        command.postalCode(),
                        new AccountId(command.accountId()),
                        command.warehouseId())){
            throw new IllegalArgumentException("Another warehouse with the same address already exists.");
        }

        warehouseToUpdate.updateInformation(command);

        try {
            var updatedWarehouse = warehouseRepository.save(warehouseToUpdate);
            return Optional.of(updatedWarehouse);
        } catch (Exception e) {
            throw new RuntimeException("Error updating warehouse: " + e.getMessage(), e);
        }
    }

    /**
     * Handles the command to delete a warehouse.
     *
     * @param command the command containing the ID of the warehouse to be deleted
     * @throws IllegalArgumentException if the warehouse does not exist
     */
    @Transactional
    @Override
    public void handle(DeleteWarehouseCommand command) {
        if (!warehouseRepository.existsById(command.warehouseId()))
            throw new IllegalArgumentException("Warehouse with ID %s does not exist".formatted(command.warehouseId()));

        try {
            warehouseRepository.deleteById(command.warehouseId());
        } catch (Exception e) {
            throw new RuntimeException("Error finding warehouse: " + e.getMessage(), e);
        }
    }
}
