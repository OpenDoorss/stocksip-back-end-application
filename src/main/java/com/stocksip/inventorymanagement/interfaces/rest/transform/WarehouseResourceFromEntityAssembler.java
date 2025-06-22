package com.stocksip.inventorymanagement.interfaces.rest.transform;

import com.stocksip.inventorymanagement.domain.model.aggregates.Warehouse;
import com.stocksip.inventorymanagement.interfaces.rest.resources.WarehouseResource;

/**
 * Assembler class to create a WarehouseResource from a Warehouse entity.
 *
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
        return new WarehouseResource(
                entity.getWarehouseId(),
                entity.getName(),
                entity.getAddress().street(),
                entity.getAddress().city(),
                entity.getAddress().district(),
                entity.getAddress().postalCode(),
                entity.getAddress().country(),
                entity.getTemperature().maxTemperature(),
                entity.getTemperature().minTemperature(),
                entity.getCapacity().capacity(),
                entity.getImageUrl().imageUrl());
    }
}
