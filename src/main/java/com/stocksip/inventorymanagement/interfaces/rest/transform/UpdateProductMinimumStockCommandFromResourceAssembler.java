package com.stocksip.inventorymanagement.interfaces.rest.transform;

import com.stocksip.inventorymanagement.domain.model.commands.UpdateProductMinimumStockCommand;
import com.stocksip.inventorymanagement.interfaces.rest.resources.UpdateProductMinimumStockResource;

/**
 * This class is responsible for transforming an UpdateProductMinimumStockCommandFromResource into an UpdateProductMinimumStockCommand.
 */
public class UpdateProductMinimumStockCommandFromResourceAssembler {

    /**
     * Method to transform an UpdateProductMinimumStockCommandFromResource into an UpdateProductMinimumStockCommand.
     * @param resource The UpdateProductMinimumStockCommandFromResource to transform.
     * @param productId The Product ID of the product whose minimum stock level will be updated.
     * @return The Created UpdateProductMinimumStockCommand command.
     */
    public static UpdateProductMinimumStockCommand toCommandFromResource(UpdateProductMinimumStockResource resource, Long productId) {
        return new UpdateProductMinimumStockCommand(
                productId,
                resource.updatedMinimumStock()
        );
    }
}
