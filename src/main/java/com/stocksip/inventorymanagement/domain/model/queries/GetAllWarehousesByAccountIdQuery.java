package com.stocksip.inventorymanagement.domain.model.queries;

import com.stocksip.inventorymanagement.domain.model.valueobjects.AccountId;

/**
 * This query is used to retrieve all the inventories of a specific account ID.
 * @param accountId The account ID used to retrieve all the warehouses.
 */
public record GetAllWarehousesByAccountIdQuery(AccountId accountId) {
}
