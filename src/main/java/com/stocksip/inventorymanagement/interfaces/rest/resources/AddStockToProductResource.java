package com.stocksip.inventorymanagement.interfaces.rest.resources;

import java.util.Date;

/**
 * ProductResource is a record that represents an AddStockToProductResource resource in the REST API.
 *
 * @summary
 * This record encapsulates the details of an AddStockToProductCommand.
 *
 * @since 1.0.0
 */
public record AddStockToProductResource(
        Date stockBestBeforeDate,
        int addedQuantity
) {
}
