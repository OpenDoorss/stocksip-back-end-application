package com.stocksip.inventorymanagement.interfaces.rest.transform;

import com.stocksip.inventorymanagement.domain.model.aggregates.Warehouse;
import com.stocksip.inventorymanagement.interfaces.rest.resources.WarehouseResource;

/**
 * Assembler class to create a WarehouseResource from a Warehouse entity.
 * @since 1.0.0
 */
public class WarehouseResourceFromEntityAssembler {

    /**
     * Converts a Warehouse entity to a WarehouseResource.
     *
     * @param entity the Warehouse entity to convert
     * @return a WarehouseResource created from the entity
     */
    public static WarehouseResource toResourceFromEntity(Warehouse entity) {
        return new WarehouseResource(entity.getName(),
                                     entity.getAddress(),
                                     entity.getWarehouseTemperature(),
                                     entity.getWarehouseCapacity(),
                                     entity.getImageUrl());
    }
}
