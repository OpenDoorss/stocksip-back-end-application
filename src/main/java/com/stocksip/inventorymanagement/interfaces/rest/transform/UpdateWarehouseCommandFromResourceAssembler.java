package com.stocksip.inventorymanagement.interfaces.rest.transform;

import com.stocksip.inventorymanagement.domain.model.commands.UpdateWarehouseCommand;
import com.stocksip.inventorymanagement.interfaces.rest.resources.UpdateWarehouseResource;

/**
 * Assembler for converting UpdateWarehouseResource to UpdateWarehouseCommand.
 * This class provides a method to transform the resource into a command object.
 *
 * @since 1.0.0
 */
public class UpdateWarehouseCommandFromResourceAssembler {

    /**
     * Converts an UpdateWarehouseResource to an UpdateWarehouseCommand.
     *
     * @param warehouseId the ID of the warehouse to update
     * @param resource the resource containing the details for the update
     * @return an UpdateWarehouseCommand containing the details from the resource
     * @throws IllegalArgumentException if the warehouseId is null or less than or equal to 0,
     *                                  or if the resource contains invalid data
     * @since 1.0.0
     */
    public static UpdateWarehouseCommand toCommandFromResource(Long warehouseId, UpdateWarehouseResource resource, Long profileId) {
        return new UpdateWarehouseCommand(
                warehouseId,
                resource.name(),
                resource.address(),
                resource.warehouseTemperature(),
                resource.warehouseCapacity(),
                resource.imageUrl(),
                profileId
        );
    }
}
