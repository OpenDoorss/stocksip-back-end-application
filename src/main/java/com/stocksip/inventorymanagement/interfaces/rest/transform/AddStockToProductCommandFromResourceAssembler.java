package com.stocksip.inventorymanagement.interfaces.rest.transform;

import com.stocksip.inventorymanagement.domain.model.commands.AddStockToProductCommand;
import com.stocksip.inventorymanagement.interfaces.rest.resources.AddStockToProductResource;

/**
 * This class is responsible for transforming an AddStockToProductResource into an AddStockToProductCommand.
 */
public class AddStockToProductCommandFromResourceAssembler {

    /**
     * Transforms an AddStockToProductResource into an AddStockToProductCommand.
     * @param resource The AddStockToProductResource to transform.
     * @param productId The identifier of the product that will receive the added stock.
     * @param warehouseId The identifier of the warehouse that stores the product whose stock will be updated.
     * @return The AddStockToProductCommand created from the resource.
     */
    public static AddStockToProductCommand toCommandFromResource(AddStockToProductResource resource, Long productId, Long warehouseId) {
        return new AddStockToProductCommand(
                productId,
                warehouseId,
                resource.stockBestBeforeDate(),
                resource.addedQuantity()
        );
    }
}
