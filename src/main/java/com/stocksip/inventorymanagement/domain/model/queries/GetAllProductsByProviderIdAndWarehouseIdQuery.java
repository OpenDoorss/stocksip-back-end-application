package com.stocksip.inventorymanagement.domain.model.queries;

import com.cloudinary.provisioning.Account;
import com.stocksip.inventorymanagement.domain.model.valueobjects.AccountId;

/**
 * GetAllProductsByProviderIdAndWarehouseIdQuery
 *
 * @summary
 * GetAllProductsByProviderIdQuery is a query to retrieve all products associated with a specific provider in a specific warehouse.
 *
 * @param warehouseId The unique identifier of the warehouse that store the products that will be retrieved.
 * @param providerId The unique identifier of the provider whose provided products are to be retrieved.
 */
public record GetAllProductsByProviderIdAndWarehouseIdQuery(Long warehouseId, AccountId accountId) {

    /**
     * Validates the query parameters.
     */
    public GetAllProductsByProviderIdAndWarehouseIdQuery {
        if (accountId == null) {
            throw new IllegalArgumentException("Provider Id cannot be null");
        }
        if (warehouseId == null) {
            throw new IllegalArgumentException("Warehouse Id cannot be null");
        }
    }
}
