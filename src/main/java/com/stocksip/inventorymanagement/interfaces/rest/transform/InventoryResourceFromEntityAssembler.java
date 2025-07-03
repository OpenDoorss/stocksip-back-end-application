package com.stocksip.inventorymanagement.interfaces.rest.transform;

import com.stocksip.inventorymanagement.domain.model.aggregates.Inventory;
import com.stocksip.inventorymanagement.interfaces.rest.resources.InventoryResource;

/**
 * This class is responsible for transforming an Inventory entity to an InventoryResource.
 */
public class InventoryResourceFromEntityAssembler {

    /**
     * Transforms an Inventory entity to an InventoryResource.
     * @param entity The entity to be transformed.
     * @return The created InventoryResource.
     */
    public static InventoryResource toResourceFromEntity(Inventory entity) {
        return new InventoryResource(
                entity.getInventoryId(),
                entity.getProduct().getProductId(),
                entity.getWarehouse().getWarehouseId(),
                entity.getProductBestBeforeDate().date(),
                entity.getProductStock().getStock(),
                entity.getProductState().toString()
        );
    }
}
