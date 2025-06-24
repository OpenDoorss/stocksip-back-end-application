package com.stocksip.inventorymanagement.domain.model.commands;

/**
 * Command to register a product exit from a warehouse.
 *
 * @param productId    The ID of the product being exited.
 * @param warehouseId  The ID of the warehouse from which the product is exiting.
 * @param stockToExit  The quantity of stock to exit.
 * @param exitReason   The reason for the product exit.
 */
public record RegisterProductExitCommand(Long productId, Long warehouseId, int stockToExit, String exitReason) {

    /**
     * Validates the command parameters.
     *
     * @throws IllegalArgumentException if any parameter is invalid.
     */
    public RegisterProductExitCommand {
        if (productId == null || warehouseId == null || stockToExit <= 0 || exitReason == null || exitReason.isBlank()) {
            throw new IllegalArgumentException("Invalid command parameters");
        }
    }
}
