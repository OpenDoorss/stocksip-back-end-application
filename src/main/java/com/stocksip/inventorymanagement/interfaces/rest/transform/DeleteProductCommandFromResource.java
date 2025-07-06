package com.stocksip.inventorymanagement.interfaces.rest.transform;

import com.stocksip.inventorymanagement.domain.model.commands.DeleteProductCommand;

/**
 * This class is responsible for transforming a DeleteProductCommandFromResource into a DeleteProductCommand.
 */
public class DeleteProductCommandFromResource {

    /**
     * Method to transform a DeleteProductCommandFromResource into a DeleteProductCommand.
     * @param productId The product that will be deleted.
     * @return The created DeleteProductCommand command.
     */
    public static DeleteProductCommand toCommandFromResource(Long productId)
    {
        return new DeleteProductCommand(productId);
    }
}
