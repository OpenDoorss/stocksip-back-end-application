package com.stocksip.inventorymanagement.interfaces.rest;

import com.stocksip.inventorymanagement.domain.model.aggregates.Product;
import com.stocksip.inventorymanagement.domain.model.aggregates.Warehouse;
import com.stocksip.inventorymanagement.domain.model.commands.CreateProductCommand;
import com.stocksip.inventorymanagement.domain.model.queries.GetCareGuideProductAndWarehouseQuery;
import com.stocksip.inventorymanagement.domain.services.WarehouseCommandService;
import com.stocksip.inventorymanagement.domain.services.WarehouseQueryService;
import com.stocksip.inventorymanagement.domain.model.queries.GetWarehouseByIdQuery;
import com.stocksip.inventorymanagement.interfaces.rest.resources.CareGuideResource;
import com.stocksip.inventorymanagement.interfaces.rest.transform.CareGuideResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping(value = "/api/v1/careguides/{careGuideId}/careguide", produces = APPLICATION_JSON_VALUE)
@Tag(name = "CareGuides")
public class CareGuideController {
    private final WarehouseCommandService warehouseCommandService;
    private final WarehouseQueryService warehouseQueryService;

    public CareGuideController(WarehouseCommandService warehouseCommandService, 
                              WarehouseQueryService warehouseQueryService) {
        this.warehouseCommandService = warehouseCommandService;
        this.warehouseQueryService = warehouseQueryService;
    }

    @PostMapping("/{careguideId}")
    @Operation(summary = "Add a care guide")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Care guide added successfully"),
            @ApiResponse(responseCode = "404", description = "Care guide not found")
    })
    public ResponseEntity<CareGuideResource> addCareGuide(
            @PathVariable Long careGuideId, 
            @PathVariable Long productId) {
        
        // First, fetch the warehouse using the careGuideId
        var getWarehouseQuery = new GetWarehouseByIdQuery(careGuideId);
        var warehouse = warehouseQueryService.handle(getWarehouseQuery);
        
        if (warehouse.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        // For now, we'll return a 201 with a basic CareGuideResource
        // since we don't have a proper way to create a care guide yet
        var careGuideResource = new CareGuideResource(
            careGuideId,
            "Default Guide Name", // You'll need to set these values appropriately
            "DEFAULT_TYPE",       // based on your requirements
            "Default description"
        );
        
        return new ResponseEntity<>(careGuideResource, HttpStatus.CREATED);
    }
}
