package com.stocksip.inventorymanagement.interfaces.rest;

import com.stocksip.inventorymanagement.domain.model.aggregates.Inventory;
import com.stocksip.inventorymanagement.domain.model.aggregates.Product;
import com.stocksip.inventorymanagement.domain.model.commands.DeleteProductCommand;
import com.stocksip.inventorymanagement.domain.model.commands.DeleteProductFromWarehouseCommand;
import com.stocksip.inventorymanagement.domain.model.commands.MoveProductToAnotherWarehouseCommand;
import com.stocksip.inventorymanagement.domain.model.commands.ReduceStockFromProductCommand;
import com.stocksip.inventorymanagement.domain.model.queries.GetInventoryByProductIdAndWarehouseIdAndBestBeforeDateQuery;
import com.stocksip.inventorymanagement.domain.services.InventoryCommandService;
import com.stocksip.inventorymanagement.domain.services.InventoryQueryService;
import com.stocksip.inventorymanagement.interfaces.rest.resources.*;
import com.stocksip.inventorymanagement.interfaces.rest.transform.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;

/**
 * REST controller for Warehouse Inventories.
 * @summary
 * This class provides REST endpoints for warehouse inventories.
 * @since 1.0.0
 */
@RestController
@RequestMapping(value = "api/v1/warehouses/{warehouseId}/inventories", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Warehouses")
public class WarehouseInventoriesController {

    private final InventoryQueryService inventoryQueryService;
    private final InventoryCommandService inventoryCommandService;

    public WarehouseInventoriesController(InventoryQueryService inventoryQueryService, InventoryCommandService inventoryCommandService) {
        this.inventoryCommandService = inventoryCommandService;
        this.inventoryQueryService = inventoryQueryService;
    }

    @GetMapping("product/{productId}/expiration-date/{expirationDate:date}")
    @Operation(summary = "Get a inventory by product and warehouse ID and best before date",
            description = "Retrieves the details of an inventory identified by its product and warehouse ID and best before date.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inventory found successfully"),
            @ApiResponse(responseCode = "404", description = "Inventory not found - invalid product or warehouse ID")
    })
    public ResponseEntity<InventoryResource> getInventoryByProductIdAndWarehouseIdAndBestBeforeDate(
            @PathVariable Long warehouseId,
            @PathVariable Date expirationDate,
            @PathVariable Long productId
    ) {
        var getInventoryByProductIdAndWarehouseIdAndBestBeforeDateQuery = new GetInventoryByProductIdAndWarehouseIdAndBestBeforeDateQuery(productId, warehouseId, expirationDate);
        var inventory = inventoryQueryService.handle(getInventoryByProductIdAndWarehouseIdAndBestBeforeDateQuery);
        if (inventory.isEmpty()) return ResponseEntity.notFound().build();
        var inventoryEntity = inventory.get();
        var inventoryResource = InventoryResourceFromEntityAssembler.toResourceFromEntity(inventoryEntity);
        return ResponseEntity.ok(inventoryResource);
    }

    @PutMapping("product/{productId}/moves")
    @Operation(summary = "Move products to another warehouse",
            description = "Moves products from one warehouse to another by their product ID, source warehouse ID, and destination warehouse ID. Then creates a new inventory or updates the existing one.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Stock moved successfully!"),
            @ApiResponse(responseCode = "400", description = "Stock could not be moved - invalid product or warehouse ID or resource")
    })
    public ResponseEntity<InventoryResource> moveProductsToAnotherWarehouse(
            @RequestBody MoveProductStockToAnotherWarehouseResource resource,
            @PathVariable Long productId,
            @PathVariable Long warehouseId
    ) {
        var moveProductsToAnotherWarehouseCommand = MoveProductsToAnotherWarehouseCommandFromResourceAssembler.toCommandFromResource(resource, productId, warehouseId);
        var inventory = inventoryCommandService.handle(moveProductsToAnotherWarehouseCommand);
        if (inventory.isEmpty()) return ResponseEntity.badRequest().build();
        var updatedInventoryEntity = inventory.get();
        var updatedInventoryResource = InventoryResourceFromEntityAssembler.toResourceFromEntity(updatedInventoryEntity);
        return ResponseEntity.ok(updatedInventoryResource);
    }

    @PutMapping("product/{productId}/additions")
    @Operation(summary = "Add stock to a product in a warehouse",
            description = "Adds stock to a product in a warehouse by its product ID, warehouse ID, and expiration date")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Stock added successfully!"),
            @ApiResponse(responseCode = "400", description = "Stock could not be moved - invalid product or warehouse ID or resource")
    })
    public ResponseEntity<InventoryResource> addStockToProduct(
            @RequestBody AddStockToProductResource resource,
            @PathVariable Long productId,
            @PathVariable Long warehouseId
    ) {
        var addStockToProductCommand = AddStockToProductCommandFromResourceAssembler.toCommandFromResource(resource, productId, warehouseId);
        var inventory = inventoryCommandService.handle(addStockToProductCommand);
        if (inventory.isEmpty()) return ResponseEntity.badRequest().build();
        var updatedInventoryEntity = inventory.get();
        var updatedInventoryResource = InventoryResourceFromEntityAssembler.toResourceFromEntity(updatedInventoryEntity);
        return ResponseEntity.ok(updatedInventoryResource);
    }

    @PutMapping("product/{productId}/substractions")
    @Operation(summary = "Decrease stock from a product in a warehouse",
            description = "Decreases stock from a product in a warehouse by its product ID, warehouse ID, and expiration date.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Stock subtracted successfully!"),
            @ApiResponse(responseCode = "400", description = "Stock could not be subtracted - invalid product or warehouse ID or resource")
    })
    public ResponseEntity<InventoryResource> decreaseStockFromProduct(
            @RequestBody DecreaseStockFromProductResource resource,
            @PathVariable Long productId,
            @PathVariable Long warehouseId
    ) {
        var decreaseStockFromProductCommand = DecreaseStockFromProductCommandFromResourceAssembler.toCommandFromResource(resource, productId, warehouseId);
        var inventory = inventoryCommandService.handle(decreaseStockFromProductCommand);
        if (inventory.isEmpty()) return ResponseEntity.badRequest().build();
        var updatedInventoryEntity = inventory.get();
        var updatedInventoryResource = InventoryResourceFromEntityAssembler.toResourceFromEntity(updatedInventoryEntity);
        return ResponseEntity.ok(updatedInventoryResource);
    }

    @PostMapping("product/{productId}")
    @Operation(
            summary = "Create a new inventory",
            description = "Creates a new inventory with the provided details."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Inventory created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public ResponseEntity<InventoryResource> addProductsToWarehouse(
            @RequestBody AddProductsToWarehouseResource resource,
            @PathVariable Long productId,
            @PathVariable Long warehouseId
    ) {
        Optional<Inventory> inventory = inventoryCommandService.handle(AddProductsToWarehouseCommandFromResourceAssembler.toCommandFromResource(resource, productId, warehouseId));

        return inventory.map(source ->
                        new ResponseEntity<>(InventoryResourceFromEntityAssembler.toResourceFromEntity(source), CREATED))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    public ResponseEntity<?> deleteProductFromWarehouse(
            @RequestBody DeleteProductFromWarehouseResource resource,
            @PathVariable Long productId,
            @PathVariable Long warehouseId
    ) {
        var deleteProductFromWarehouseCommand = new DeleteProductFromWarehouseCommand(warehouseId, productId, resource.stockBestBeforeDate());
        inventoryCommandService.handle(deleteProductFromWarehouseCommand);
        return ResponseEntity.ok("Inventory of given Product and Warehouse Id deleted successfully.");
    }
}
