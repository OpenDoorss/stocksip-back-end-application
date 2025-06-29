package com.stocksip.inventorymanagement.interfaces.rest;

import com.stocksip.inventorymanagement.domain.model.aggregates.Product;
import com.stocksip.inventorymanagement.domain.model.aggregates.Warehouse;
import com.stocksip.inventorymanagement.domain.model.commands.CreateCareGuideCommand;
import com.stocksip.inventorymanagement.domain.model.commands.CreateProductCommand;
import com.stocksip.inventorymanagement.domain.model.queries.GetCareGuideProductAndWarehouseQuery;
import com.stocksip.inventorymanagement.domain.services.CareGuideCommandService;
import com.stocksip.inventorymanagement.domain.services.WarehouseCommandService;
import com.stocksip.inventorymanagement.domain.services.WarehouseQueryService;
import com.stocksip.inventorymanagement.application.internal.outboundservices.cloudinary.CloudinaryService;
import com.stocksip.inventorymanagement.domain.model.queries.GetWarehouseByIdQuery;
import com.stocksip.inventorymanagement.interfaces.rest.resources.CareGuideResource;
import com.stocksip.inventorymanagement.interfaces.rest.transform.CareGuideResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping(value = "/api/v1/careguides", produces = APPLICATION_JSON_VALUE)
@Tag(name = "CareGuides")
public class CareGuideController {
    private final WarehouseCommandService warehouseCommandService;
    private final WarehouseQueryService warehouseQueryService;
    private final CloudinaryService cloudinaryService;
    private final CareGuideCommandService careGuideCommandService;

    @Autowired
    public CareGuideController(WarehouseCommandService warehouseCommandService, 
                             WarehouseQueryService warehouseQueryService,
                             CloudinaryService cloudinaryService,
                             CareGuideCommandService careGuideCommandService) {
        this.warehouseCommandService = warehouseCommandService;
        this.warehouseQueryService = warehouseQueryService;
        this.cloudinaryService = cloudinaryService;
        this.careGuideCommandService = careGuideCommandService;
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
                imageUrl = cloudinaryService.UploadImage(imageFile);
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
            imageUrl != null ? imageUrl : ""
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
}
