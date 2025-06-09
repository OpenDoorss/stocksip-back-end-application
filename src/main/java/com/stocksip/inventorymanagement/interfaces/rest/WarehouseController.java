package com.stocksip.inventorymanagement.interfaces.rest;

import com.stocksip.inventorymanagement.domain.model.aggregates.Warehouse;
import com.stocksip.inventorymanagement.domain.services.WarehouseCommandService;
import com.stocksip.inventorymanagement.domain.services.WarehouseQueryService;
import com.stocksip.inventorymanagement.interfaces.rest.resources.CreateWarehouseResource;
import com.stocksip.inventorymanagement.interfaces.rest.resources.UpdateWarehouseResource;
import com.stocksip.inventorymanagement.interfaces.rest.resources.WarehouseResource;
import com.stocksip.inventorymanagement.interfaces.rest.transform.CreateWarehouseCommandFromResourceAssembler;
import com.stocksip.inventorymanagement.interfaces.rest.transform.UpdateWarehouseCommandFromResourceAssembler;
import com.stocksip.inventorymanagement.interfaces.rest.transform.WarehouseResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * REST controller for Warehouses.
 * @summary
 * This class provides REST endpoints for warehouses.
 * @since 1.0.0
 */
@RestController
@RequestMapping(value = "api/v1/warehouses", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Warehouses", description = "Endpoints for managing warehouses.")
public class WarehouseController {

    private final WarehouseCommandService warehouseCommandService;
    private final WarehouseQueryService warehouseQueryService;

    /**
     * Constructor for WarehouseController.
     * @param warehouseCommandService Warehouse command service
     * @param warehouseQueryService Warehouse source query service
     * @since 1.0.0
     * @see WarehouseCommandService
     * @see WarehouseQueryService
     */
    public WarehouseController(WarehouseCommandService warehouseCommandService, WarehouseQueryService warehouseQueryService) {
        this.warehouseCommandService = warehouseCommandService;
        this.warehouseQueryService = warehouseQueryService;
    }

    /**
     * Create a new warehouse.
     * @param resource CreateWarehouseResource containing the details of the warehouse to be created
     * @return ResponseEntity containing the created WarehouseResource or a bad request if the resource is invalid
     * @see CreateWarehouseResource
     * @see WarehouseResource
     *
     * @since 1.0.0
     */
    @Operation(
            summary = "Create a new warehouse",
            description = "Creates a new warehouse with the provided details."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Warehouse created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PostMapping
    public ResponseEntity<WarehouseResource> createWarehouse(@RequestBody CreateWarehouseResource resource) {
        Optional<Warehouse> warehouse = warehouseCommandService.handle(CreateWarehouseCommandFromResourceAssembler.toCommandFromResource(resource));

        return warehouse.map(source ->
                new ResponseEntity<>(WarehouseResourceFromEntityAssembler.toResourceFromEntity(source), CREATED))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    /**
     * Get a warehouse by its ID.
     * @param warehouseId ID of the warehouse to retrieve
     * @param updateWarehouseResource The {@link UpdateWarehouseResource} containing the updated details of the warehouse
     * @return ResponseEntity containing the WarehouseResource or a not found response if the warehouse does not exist
     * @see WarehouseResource
     *
     * @since 1.0.0
     */
    @Operation(summary = "Update an existing warehouse",
            description = "Updates the details of an existing warehouse identified by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Warehouse updated successfully"),
            @ApiResponse(responseCode = "404", description = "Bad request - invalid warehouse ID or resource")
    })
    @PutMapping("/{warehouseId}")
    public ResponseEntity<WarehouseResource> updateWarehouse(@PathVariable Long warehouseId, @RequestBody UpdateWarehouseResource updateWarehouseResource) {
        var updateWarehouseCommand = UpdateWarehouseCommandFromResourceAssembler.toCommandFromResource(warehouseId, updateWarehouseResource);
        var updatedWarehouse = warehouseCommandService.handle(updateWarehouseCommand);
        if (updatedWarehouse.isEmpty()) return ResponseEntity.badRequest().build();
        var updatedWarehouseEntity = updatedWarehouse.get();
        var updatedWarehouseResource = WarehouseResourceFromEntityAssembler.toResourceFromEntity(updatedWarehouseEntity);
        return ResponseEntity.ok(updatedWarehouseResource);
    }



}
