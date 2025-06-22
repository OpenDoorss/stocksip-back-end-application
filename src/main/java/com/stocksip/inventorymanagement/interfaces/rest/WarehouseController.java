package com.stocksip.inventorymanagement.interfaces.rest;

import com.stocksip.inventorymanagement.domain.model.aggregates.Warehouse;
import com.stocksip.inventorymanagement.domain.model.commands.DeleteWarehouseCommand;
import com.stocksip.inventorymanagement.domain.model.queries.GetAllWarehousesByIdQuery;
import com.stocksip.inventorymanagement.domain.model.queries.GetWarehouseByIdQuery;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;

/**
 * REST controller for Warehouses.
 * @summary
 * This class provides REST endpoints for warehouses.
 * @since 1.0.0
 */
@RestController
@RequestMapping(value = "api/v1/warehouses", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Warehouses", description = "Endpoints for managing warehouses.")
@CrossOrigin(origins = "http://localhost:4200",
        allowedHeaders = "*",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
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
     * Updates a warehouse by its ID.
     * @param warehouseId ID of the warehouse to update
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
            @ApiResponse(responseCode = "404", description = "Course not found - invalid warehouse ID or resource")
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

    /**
     * Get a warehouse by its ID.
     * @param warehouseId ID of the warehouse to retrieve
     * @return ResponseEntity containing the WarehouseResource or a bad request if the warehouse does not exist
     * @see WarehouseResource
     *
     * @since 1.0.0
     */
    @Operation(summary = "Get a warehouse by ID",
            description = "Retrieves the details of a warehouse identified by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Warehouse found successfully"),
            @ApiResponse(responseCode = "404", description = "Course not found - invalid warehouse ID")
    })
    @GetMapping("/{warehouseId}")
    public ResponseEntity<WarehouseResource> getWarehouseById(@PathVariable Long warehouseId) {

        var getWarehouseById = new GetWarehouseByIdQuery(warehouseId);
        var warehouse = warehouseQueryService.handle(getWarehouseById);
        if (warehouse.isEmpty()) return ResponseEntity.notFound().build();
        var warehouseEntity = warehouse.get();
        var warehouseResource = WarehouseResourceFromEntityAssembler.toResourceFromEntity(warehouseEntity);
        return ResponseEntity.ok(warehouseResource);
    }

    /**
     * Get all warehouses associated with a specific account ID.
     * @return ResponseEntity containing a list of WarehouseResources
     * @see WarehouseResource
     *
     * @since 1.0.0
     */
    @Operation(summary = "Get all warehouses by account ID",
        description = "Retrieves all warehouses associated with a specific profile ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Warehouses retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request - invalid profile ID")
    })
    @GetMapping
    public ResponseEntity<List<WarehouseResource>> getAllWarehouses()
    {
        var getAllWarehousesByIdQuery = new GetAllWarehousesByIdQuery();
        var warehouses = warehouseQueryService.handle(getAllWarehousesByIdQuery);
        var warehouseResources = warehouses.stream().map(WarehouseResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(warehouseResources);
    }

    /**
     * Delete a warehouse by its ID.
     * @param warehouseId ID of the warehouse to delete
     * @return ResponseEntity indicating the result of the deletion operation
     * @since 1.0.0
     */
    @Operation(summary = "Delete a warehouse by ID",
            description = "Deletes a warehouse identified by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Warehouse deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request - invalid warehouse ID")
    })
    @DeleteMapping("/{warehouseId}")
    public ResponseEntity<?> deleteWarehouse(@PathVariable Long warehouseId) {
        var deleteWarehouseCommand = new DeleteWarehouseCommand(warehouseId);
        warehouseCommandService.handle(deleteWarehouseCommand);
        return ResponseEntity.ok("Warehouse with given Id deleted successfully.");
    }

}
