package com.stocksip.inventorymanagement.application.internal.commandservices;

import com.stocksip.inventorymanagement.application.internal.outboundservices.cloudinary.CloudinaryService;
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

    /**
     * Service for handling cloudinary operations.
     */
    private final CloudinaryService cloudinaryService;

    public WarehouseCommandServiceImpl(WarehouseRepository warehouseRepository, CloudinaryService cloudinaryService) {
        this.warehouseRepository = warehouseRepository;
        this.cloudinaryService = cloudinaryService;
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

        String imageUrl = command.image() != null ? cloudinaryService.UploadImage(command.image())
                : "https://res.cloudinary.com/deuy1pr9e/image/upload/v1750914969/default-warehouse_whqolq.avif";

        var warehouse = new Warehouse(command, imageUrl);
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

        Long accountId = warehouseRepository.findAccountIdByWarehouseId(command.warehouseId());

        var warehouseToUpdate = warehouseRepository.findWarehouseByWarehouseIdAndAccountId(command.warehouseId(), new AccountId(accountId))
                .orElseThrow(() -> new IllegalArgumentException("Warehouse with ID %s does not exist".formatted(command.warehouseId())));

        if (warehouseRepository.existsByNameAndAccountIdAndWarehouseIdIsNot(command.name(), new AccountId(accountId), command.warehouseId())) {
            throw new IllegalArgumentException("Warehouse with name %s already exists".formatted(command.name()));
        }

        if (warehouseRepository.existsByAddressStreetIgnoreCaseAndAddressCityIgnoreCaseAndAddressPostalCodeIgnoreCaseAndAccountIdAndWarehouseIdIsNot(
                        command.street(),
                        command.city(),
                        command.postalCode(),
                        new AccountId(accountId),
                        command.warehouseId())){
            throw new IllegalArgumentException("Another warehouse with the same address already exists.");
        }

        String currentImageUrl = warehouseToUpdate.getImageUrl().imageUrl();
        String imageUrl = currentImageUrl;

        if (command.image() != null && !command.image().isEmpty()) {
            cloudinaryService.DeleteImage(currentImageUrl);
            imageUrl = cloudinaryService.UploadImage(command.image());
        }

        warehouseToUpdate.updateInformation(command, imageUrl);

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

        String imageUrl = warehouseRepository.findImageUrlByWarehouseId(command.warehouseId());

        try {
            warehouseRepository.deleteById(command.warehouseId());
            cloudinaryService.DeleteImage(imageUrl);
        } catch (Exception e) {
            throw new RuntimeException("Error finding warehouse: " + e.getMessage(), e);
        }
    }
}
