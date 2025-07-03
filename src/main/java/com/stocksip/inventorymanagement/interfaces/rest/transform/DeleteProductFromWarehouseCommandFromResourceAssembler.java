package com.stocksip.inventorymanagement.interfaces.rest.transform;

import com.stocksip.inventorymanagement.domain.model.commands.DeleteProductFromWarehouseCommand;
import com.stocksip.inventorymanagement.interfaces.rest.resources.DeleteProductFromWarehouseResource;

/**
 * This class is responsible for transforming a DeleteProductFromWarehouseResource into a DeleteProductFromWarehouseCommand.
 */
public class DeleteProductFromWarehouseCommandFromResourceAssembler {

    /**
     * Method to transform a DeleteProductFromWarehouseResource into a DeleteProductFromWarehouseCommand.
     * @param resource The DeleteProductFromWarehouseResource to be transformed.
     * @param productId The identifier of the product that will be deleted.
     * @param warehouseId The identifier of the warehouse that won't store the product no more.
     * @return The resulting DeleteProductFromWarehouseCommand created from the resource.
     */
    public static DeleteProductFromWarehouseCommand toCommandFromResource(DeleteProductFromWarehouseResource resource, Long productId, Long warehouseId) {
        return new DeleteProductFromWarehouseCommand(
                productId,
                warehouseId,
                resource.stockBestBeforeDate()
        );
    }
}
