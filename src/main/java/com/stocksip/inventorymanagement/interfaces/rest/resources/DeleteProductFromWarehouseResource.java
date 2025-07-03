package com.stocksip.inventorymanagement.interfaces.rest.resources;

import java.util.Date;

/**
 * DeleteProductFromWarehouseResource is a record that represents a DeleteProductFromWarehouseCommand resource in the REST API.
 *
 * @summary
 * This record encapsulates the details of a DeleteProductFromWarehouseResource.
 *
 * @since 1.0.0
 */
public record DeleteProductFromWarehouseResource(
        Date stockBestBeforeDate
) {
}
