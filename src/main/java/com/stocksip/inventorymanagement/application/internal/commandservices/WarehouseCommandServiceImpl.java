package com.stocksip.inventorymanagement.application.internal.commandservices;

import com.stocksip.inventorymanagement.domain.model.aggregates.Warehouse;
import com.stocksip.inventorymanagement.domain.model.commands.CreateWarehouseCommand;
import com.stocksip.inventorymanagement.domain.model.commands.DeleteWarehouseCommand;
import com.stocksip.inventorymanagement.domain.model.commands.UpdateWarehouseCommand;
import com.stocksip.inventorymanagement.domain.model.commands.UploadImageCommand;
import com.stocksip.inventorymanagement.domain.model.valueobjects.ProfileId;
import com.stocksip.inventorymanagement.domain.services.WarehouseCommandService;
import com.stocksip.inventorymanagement.infrastructure.persistence.jpa.WarehouseRepository;
import com.stocksip.inventorymanagement.infrastructure.storage.cloudinary.CloudinaryService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
    private final CloudinaryService cloudinaryService;

    public WarehouseCommandServiceImpl(WarehouseRepository warehouseRepository, CloudinaryService cloudinaryService) {
        this.warehouseRepository = warehouseRepository;
        this.cloudinaryService = cloudinaryService;
    }

    /**
     * Handles the command to create a new warehouse.
     *
     * @param command the command containing the details of the warehouse to create
     * @return an Optional containing the created Warehouse if successful, or empty if not
     * @throws IllegalArgumentException if a warehouse with the same name or address already exists
     */
    @Override
    public Optional<Warehouse> handle(CreateWarehouseCommand command) {
        validateWarehouseUniqueness(command);
        var warehouse = new Warehouse(command);
        var createdWarehouse = warehouseRepository.save(warehouse);
        return Optional.of(createdWarehouse);
    }

    /**
     * Handles the command to create a new warehouse with an image.
     *
     * @param warehouseCommand the command containing the details of the warehouse to create
     * @param imageCommand the command containing the image upload details
     * @return an Optional containing the created Warehouse if successful, or empty if not
     * @throws IllegalArgumentException if a warehouse with the same name or address already exists
     */
    @Override
    public Optional<Warehouse> handle(CreateWarehouseCommand warehouseCommand, UploadImageCommand imageCommand) {
        validateWarehouseUniqueness(warehouseCommand);

        String imageUrl;
        try {
            imageUrl = cloudinaryService.uploadImage(imageCommand.imageFile());
        } catch (IOException e) {
            throw new RuntimeException("Error uploading image: " + e.getMessage(), e);
        }

        var warehouse = new Warehouse(
                warehouseCommand,
                imageUrl
        );

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

        var warehouseToUpdate = warehouseRepository.findWarehouseByWarehouseIdAndProfileId(command.warehouseId(), ProfileId.from(command.profileId()))
                .orElseThrow(() -> new IllegalArgumentException("Warehouse with ID %s does not exist".formatted(command.warehouseId())));

        if (!warehouseToUpdate.getName().equals(command.name()) &&
                warehouseRepository.existsByNameAndProfileIdAndWarehouseIdIsNot(command.name(), ProfileId.from(command.profileId()), command.warehouseId())) {
            throw new IllegalArgumentException("Warehouse with name %s already exists".formatted(command.name()));
        }

        boolean isAddressChanged = !warehouseToUpdate.getAddress().equals(command.address());
        if (isAddressChanged &&
                warehouseRepository.existsByAddressStreetIgnoreCaseAndAddressCityIgnoreCaseAndAddressPostalCodeIgnoreCaseAndProfileIdAndWarehouseIdIsNot(
                        command.address().street(),
                        command.address().city(),
                        command.address().postalCode(),
                        ProfileId.from(command.profileId()),
                        command.warehouseId())){
            throw new IllegalArgumentException("Another warehouse with the same address already exists.");
        }

        warehouseToUpdate.updateInformation(
                command.name(),
                command.address(),
                command.temperature(),
                command.capacity(),
                command.imageUrl()
        );

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

    /**
     * Validates the uniqueness of the warehouse based on its name and address.
     *
     * @param command the command containing the details of the warehouse to validate
     * @throws IllegalArgumentException if a warehouse with the same name or address already exists
     */
    private void validateWarehouseUniqueness(CreateWarehouseCommand command) {
        if (warehouseRepository.existsByNameIgnoreCaseAndProfileId(command.name(), ProfileId.from(command.profileId())))
            throw new IllegalArgumentException("Warehouse with the same name already exists.");

        if (warehouseRepository.existsByAddressStreetAndAddressCityAndAddressPostalCodeIgnoreCaseAndProfileId(
                command.street(),
                command.city(),
                command.postalCode(),
                ProfileId.from(command.profileId()))) {
            throw new IllegalArgumentException("Warehouse with the same address already exists.");
        }
    }
}
