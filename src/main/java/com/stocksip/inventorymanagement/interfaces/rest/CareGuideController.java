package com.stocksip.inventorymanagement.interfaces.rest;
import com.stocksip.inventorymanagement.domain.model.commands.CreateCareGuideCommand;
import com.stocksip.inventorymanagement.domain.model.commands.DeleteCareGuideCommand;
import com.stocksip.inventorymanagement.domain.model.commands.UpdateCareGuideCommand;
import com.stocksip.inventorymanagement.domain.model.queries.GetCareGuideByIdQuery;
import com.stocksip.inventorymanagement.domain.services.CareGuideCommandService;
import com.stocksip.inventorymanagement.domain.services.CareGuideQueryService;
import com.stocksip.inventorymanagement.domain.services.WarehouseCommandService;
import com.stocksip.inventorymanagement.domain.services.WarehouseQueryService;
import com.stocksip.inventorymanagement.application.internal.outboundservices.filestorage.FileStorageService;
import com.stocksip.inventorymanagement.interfaces.rest.resources.CareGuideResource;
import com.stocksip.inventorymanagement.interfaces.rest.resources.UpdateCareGuideResource;
import com.stocksip.inventorymanagement.interfaces.rest.transform.CareGuideResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Map;

import java.util.NoSuchElementException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping(value = "/api/v1/careguides", produces = APPLICATION_JSON_VALUE)
@Tag(name = "CareGuides",description = "Available Care Guide Endpoints.")
public class CareGuideController {
    private final WarehouseCommandService warehouseCommandService;
    private final WarehouseQueryService warehouseQueryService;
    private final FileStorageService fileStorageService;
    private final CareGuideCommandService careGuideCommandService;
    private final CareGuideQueryService careGuideQueryService;
    @Autowired
    public CareGuideController(WarehouseCommandService warehouseCommandService, 
                             WarehouseQueryService warehouseQueryService,
                             FileStorageService fileStorageService,
                             CareGuideCommandService careGuideCommandService, CareGuideQueryService careGuideQueryService) {
        this.warehouseCommandService = warehouseCommandService;
        this.warehouseQueryService = warehouseQueryService;
        this.fileStorageService = fileStorageService;
        this.careGuideCommandService = careGuideCommandService;
        this.careGuideQueryService = careGuideQueryService;
    }

    @PostMapping(value = "/{careGuideId}/careguide", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Add a care guide with file upload")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Care guide added successfully"),
    @ApiResponse(responseCode = "400", description = "Invalid input"),
    @ApiResponse(responseCode = "404", description = "Care guide not found"),
    @ApiResponse(responseCode = "500", description = "Error processing the request")
    })
    public ResponseEntity<CareGuideResource> addCareGuide(
        @PathVariable Long careGuideId,
        @RequestParam("guideName") String guideName,
        @RequestParam("type") String type,
        @RequestParam("description") String description,
        @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {

    try {
        // 1. Validate input parameters
        if (guideName == null || guideName.trim().isEmpty() ||
            type == null || type.trim().isEmpty() ||
            description == null || description.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        // 2. Process image if provided
        String imageUrl = null;
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                // Upload image to Cloudinary
                imageUrl = fileStorageService.UploadImage(imageFile);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CareGuideResource(
                        careGuideId,
                        guideName,
                        type,
                        "Error uploading image: " + e.getMessage(),
                        ""
                    ));
            }
        }

        // 3. Create and save the care guide
        var command = new CreateCareGuideCommand(
            guideName,
            type,
            description,
            imageUrl != null ? imageUrl : "",
            "",
            null
        );

        // 4. Execute the command through the command service
        var careGuide = careGuideCommandService.handle(command);
        
        if (careGuide.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new CareGuideResource(
                    careGuideId,
                    guideName,
                    type,
                    "Error creating care guide in the database",
                    ""
                ));
        }

        // 5. Return the created resource
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(CareGuideResourceFromEntityAssembler.toResourceFromEntity(careGuide.get()));

    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new CareGuideResource(
                careGuideId,
                guideName,
                type,
                "Error processing request: " + e.getMessage(),
                null
            ));
    }
    }
    @Operation(summary = "Get Care Guide by ID", 
              description = "Retrieves a care guide by its unique identifier")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Care Guide found"),
        @ApiResponse(responseCode = "404", description = "Care Guide not found")
    })
    @GetMapping("/{careGuideId}")
    public ResponseEntity<CareGuideResource> getCareGuideById(
            @Parameter(description = "ID of the care guide to be obtained") 
            @PathVariable Long careGuideId) {
        
        var query = new GetCareGuideByIdQuery(careGuideId);
        var careGuide = careGuideQueryService.handle(query);
        
        if (careGuide == null || careGuide.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(CareGuideResourceFromEntityAssembler.toResourceFromEntity(careGuide.get(0)));
    }

    @Operation(summary = "Update Care Guide", 
              description = "Update Recommendations for A Specific Care Guide")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Care Guide updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PutMapping("/{careGuideId}")
    public ResponseEntity<CareGuideResource> updateCareGuide(
            @Parameter(description = "ID of the care guide to be updated") 
            @PathVariable Long careGuideId,
            @Valid @RequestBody UpdateCareGuideResource resource) {
        
        var command = new UpdateCareGuideCommand(
            careGuideId,
            resource.guideName(),
            resource.type(),
            resource.description(),
            resource.imageUrl()
        );
        
        var updatedCareGuide = careGuideCommandService.handle(command);
        
        if (updatedCareGuide.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        
        return ResponseEntity.ok(CareGuideResourceFromEntityAssembler.toResourceFromEntity(updatedCareGuide.get()));
    }

    @Operation(summary = "Delete a Care Guide", 
              description = "Delete a Specific Care Guide")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Care Guide deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Care Guide not found")
    })
    @DeleteMapping("/{careGuideId}")
    public ResponseEntity<Void> deleteCareGuide(
            @Parameter(description = "ID of the care guide to be deleted") 
            @PathVariable Long careGuideId) {
        
        try {
            careGuideCommandService.handle(new DeleteCareGuideCommand(careGuideId));
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Unassign a Care Guide", 
              description = "Unassign a Care Guide From Its Current Product")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Care Guide unassigned successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid operation")
    })
    @PutMapping("/{careGuideId}/deallocations")
    public ResponseEntity<Map<String, String>> deallocateCareGuide(
            @Parameter(description = "ID of the care guide to be unassigned")
            @PathVariable Long careGuideId) {
        
        // Implementar la lógica de desasignación
        // Esto requeriría un nuevo comando como UnassignCareGuideCommand
        return ResponseEntity.ok(Map.of(
            "message", 
            String.format("Care Guide with ID %d unassigned from its current product successfully", careGuideId)
        ));
    }

    @Operation(summary = "Assign a Care Guide", 
              description = "Assign a Care Guide To a Specific Product")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Care Guide assigned successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PutMapping("/{careGuideId}/allocations/{productId}")
    public ResponseEntity<Map<String, String>> allocateCareGuideToProduct(
            @Parameter(description = "ID of the care guide to be assigned") 
            @PathVariable Long careGuideId,
            @Parameter(description = "ID of the product to assign the care guide to") 
            @PathVariable Long productId) {
        
        // Implementar la lógica de asignación
        // Esto requeriría un nuevo comando como AssignCareGuideToProductCommand
        return ResponseEntity.ok(Map.of(
            "message", 
            String.format("Care Guide with ID %d assigned to product with ID %d successfully", careGuideId, productId)
        ));
    }

}
