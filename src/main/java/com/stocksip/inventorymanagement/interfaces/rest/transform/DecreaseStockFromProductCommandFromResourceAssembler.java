package com.stocksip.inventorymanagement.interfaces.rest.transform;

import com.stocksip.inventorymanagement.domain.model.commands.ReduceStockFromProductCommand;
import com.stocksip.inventorymanagement.interfaces.rest.resources.DecreaseStockFromProductResource;

/**
 * This class is responsible for transforming a DecreaseStockFromProductResource into a DecreaseStockFromProductCommand.
 */
public class DecreaseStockFromProductCommandFromResourceAssembler {

    /**
     * Method to transform a DecreaseStockFromProductResource into a DecreaseStockFromProductCommand.
     * @param resource The DecreaseStockFromProductResource to transform.
     * @param productId The identifier of the product that will decrease its stock.
     * @param warehouseId The identifier of the warehouse that stores the product whose stock will be updated.
     * @return The DecreaseStockFromProductCommand created from the resource.
     */
    public static ReduceStockFromProductCommand toCommandFromResource(DecreaseStockFromProductResource resource, Long productId, Long warehouseId) {
        return new ReduceStockFromProductCommand(
                productId,
                warehouseId,
                resource.expirationDate(),
                resource.removedQuantity()
        );
    }
}
