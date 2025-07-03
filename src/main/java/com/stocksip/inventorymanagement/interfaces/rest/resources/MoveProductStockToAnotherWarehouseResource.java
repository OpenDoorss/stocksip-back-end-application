package com.stocksip.inventorymanagement.interfaces.rest.resources;

import java.util.Date;

/**
 * MoveProductStockToAnotherWarehouseResource is a record that represents an MoveProductStockToAnotherWarehouseCommand resource in the REST API.
 *
 * @summary
 * This record encapsulates the details of an MoveProductStockToAnotherWarehouseCommand.
 *
 * @since 1.0.0
 */
public record MoveProductStockToAnotherWarehouseResource(
        Long newWarehouseId,
        Date movedStockExpirationDate,
        int movedQuantity
) {
}
