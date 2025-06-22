package com.stocksip.inventorymanagement.interfaces.rest.resources;

import java.util.Date;

/**
 * DecreaseStockFromProductResource is a record that represents a DecreaseStockFromProductCommand resource in the REST API.
 *
 * @summary
 * This record encapsulates the details of a DecreaseStockFromProductResource.
 *
 * @since 1.0.0
 */
public record DecreaseStockFromProductResource(
        Date expirationDate,
        int removedQuantity
) {
}
