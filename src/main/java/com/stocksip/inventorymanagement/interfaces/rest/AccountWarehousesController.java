package com.stocksip.inventorymanagement.interfaces.rest;

import com.stocksip.inventorymanagement.domain.model.aggregates.Warehouse;
import com.stocksip.inventorymanagement.domain.model.queries.GetAllProductsByAccountIdQuery;
import com.stocksip.inventorymanagement.domain.model.queries.GetAllWarehousesByAccountIdQuery;
import com.stocksip.inventorymanagement.domain.model.valueobjects.AccountId;
import com.stocksip.inventorymanagement.domain.services.WarehouseCommandService;
import com.stocksip.inventorymanagement.domain.services.WarehouseQueryService;
import com.stocksip.inventorymanagement.interfaces.rest.resources.WarehouseResource;
import com.stocksip.inventorymanagement.interfaces.rest.transform.ProductResourceFromEntityAssembler;
import com.stocksip.inventorymanagement.interfaces.rest.transform.WarehouseResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for Account Warehouses.
 * @summary
 * This class provides REST endpoints for account warehouses.
 * @since 1.0.0
 */
@RestController
@RequestMapping(value = "api/v1/accounts/{accountId}/warehouses", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Accounts")
public class AccountWarehousesController {

    private final WarehouseQueryService warehouseQueryService;

    public AccountWarehousesController(WarehouseQueryService warehouseQueryService) {
        this.warehouseQueryService = warehouseQueryService;
    }

    @GetMapping
    @Operation(summary = "Get all warehouses by account ID",
            description = "Retrieves all warehouses associated with a specific account ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Warehouses retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Not found - invalid account ID")
    })
    public ResponseEntity<List<WarehouseResource>> getAllWarehousesByAccountId(@PathVariable Long accountId) {

        var targetAccountId = new AccountId(accountId);
        var getAllWarehousesByAccountIdQuery = new GetAllWarehousesByAccountIdQuery(targetAccountId);
        var warehouses = warehouseQueryService.handle(getAllWarehousesByAccountIdQuery);
        if (warehouses.isEmpty()) return ResponseEntity.notFound().build();
        var warehousesEntities = warehouses.stream().map(WarehouseResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(warehousesEntities);
    }
}
