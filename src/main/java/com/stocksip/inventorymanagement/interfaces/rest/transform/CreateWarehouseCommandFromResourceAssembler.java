package com.stocksip.inventorymanagement.interfaces.rest.transform;

import com.stocksip.inventorymanagement.domain.model.commands.CreateWarehouseCommand;
import com.stocksip.inventorymanagement.interfaces.rest.resources.CreateWarehouseResource;

/**
 * Assembler to create a CreateWarehouseCommand from a CreateWarehouseResource.
 * @summary
 * This class provides a method to transform a CreateWarehouseResource into a CreateWarehouseCommand.
 *
 * @since 1.0.0
 */
public class CreateWarehouseCommandFromResourceAssembler {

    /**
     * Converts a CreateWarehouseResource to a CreateWarehouseCommand.
     * @param resource the CreateWarehouseResource to convert
     * @return CreateWarehouseCommand created from the resource
     */
    public static CreateWarehouseCommand toCommandFromResource(CreateWarehouseResource resource) {

        return new CreateWarehouseCommand(resource.name(),
                                          resource.street(),
                                          resource.city(),
                                          resource.district(),
                                          resource.postalCode(),
                                          resource.country(),
                                          resource.maxTemperature(),
                                          resource.minTemperature(),
                                          resource.capacity(),
                                          resource.imageUrl(),
                                          resource.accountId());
    }

}
