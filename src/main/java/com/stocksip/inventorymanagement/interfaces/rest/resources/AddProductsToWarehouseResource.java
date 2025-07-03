package com.stocksip.inventorymanagement.interfaces.rest.resources;

import java.util.Date;

/**
 * AddProductsToWarehouseResource is a record that represents an AddProductsToWarehouseCommand resource in the REST API.
 *
 * @summary
 * This record encapsulates the details of an AddProductsToWarehouseCommand.
 *
 * @since 1.0.0
 */
public record AddProductsToWarehouseResource(
        Date bestBeforeDate,
        int quantity) {
}
