package com.stocksip.inventorymanagement.interfaces.rest.resources;

import java.time.LocalDate;
import java.util.Date;

/**
 * InventoryResource is a record that represents a inventory resource in the REST API.
 *
 * @summary
 * This record encapsulates the details of a inventory.
 *
 * @since 1.0.0
 */
public record InventoryResource(
        Long id,
        Long productId,
        Long warehouseId,
        LocalDate bestBeforeDate,
        Integer stock,
        String productState
) {
}
