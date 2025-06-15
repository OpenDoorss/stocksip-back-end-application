package com.stocksip.analyticsreporting.interfaces.rest;

import com.stocksip.analyticsreporting.domain.model.aggregates.CareGuide;
import com.stocksip.analyticsreporting.domain.model.commands.CreateCareGuideCommand;
import com.stocksip.analyticsreporting.domain.model.commands.DeleteCareGuideCommand;
import com.stocksip.analyticsreporting.domain.model.commands.UpdateCareGuideCommand;
import com.stocksip.analyticsreporting.domain.services.CareGuideCommandService;
import com.stocksip.analyticsreporting.domain.services.CareGuideQueryService;
import com.stocksip.analyticsreporting.interfaces.rest.resources.CareGuideResource;
import com.stocksip.analyticsreporting.interfaces.rest.resources.CreateCareGuideResource;
import com.stocksip.analyticsreporting.interfaces.rest.transform.CareGuideResourceFromEntityAssembler;
import com.stocksip.analyticsreporting.interfaces.rest.transform.CreateCareGuideCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.stocksip.analyticsreporting.domain.services.ImageService;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * REST controller for CareGuide.
 * @summary
 * This class provides REST endpoints for care guides.
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api/v1/careguide", produces = APPLICATION_JSON_VALUE)
@Tag(name = "CareGuide", description = "Endpoints for careguides")
public class CareGuideController {
    private static final Logger logger = LoggerFactory.getLogger(CareGuideController.class);
    private final CareGuideCommandService careGuideCommandService;
    private final CareGuideQueryService careGuideQueryService;
    private final ImageService imageService;
    private final CreateCareGuideCommandFromResourceAssembler createCareGuideCommandFromResourceAssembler;
    
    /**
     * Constructor for CareGuideController.
     * @param careGuideCommandService CareGuide command service
     * @param careGuideQueryService CareGuide query service
     * @since 1.0
     * @see CareGuideCommandService
     * @see CareGuideQueryService
     */
    public CareGuideController(CareGuideCommandService careGuideCommandService, 
                            CareGuideQueryService careGuideQueryService,
                            ImageService imageService,
                            CreateCareGuideCommandFromResourceAssembler createCareGuideCommandFromResourceAssembler) {
        this.careGuideCommandService = careGuideCommandService;
        this.careGuideQueryService = careGuideQueryService;
        this.imageService = imageService;
        this.createCareGuideCommandFromResourceAssembler = createCareGuideCommandFromResourceAssembler;
    }
    /**
     * Creates a care guide.
     * @param resource CreateCareGuideResource containing the attributes of the care guide to be created
     * @return ResponseEntity with the created care guide resource, or bad request if the resource is invalid
     * @since 1.0
     * @see CreateCareGuideResource
     * @see CareGuideResource
     */
    @Operation(
        summary = "Create a new care guide",
        description = "Creates a new care guide with the provided details and image")
@ApiResponses(
        value = {
                @ApiResponse(responseCode = "201", description = "Care Guide created successfully"),
                @ApiResponse(responseCode = "400", description = "Invalid request")
        }
)
@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
public ResponseEntity<CareGuideResource> createCareGuide(
        @RequestPart("data") CreateCareGuideResource resource,
        @RequestPart(value = "image", required = false) MultipartFile image) {
    
    try {
        String imageUrl = "";
        if (image != null && !image.isEmpty()) {
            try {
                imageUrl = imageService.uploadImage(image);
                logger.info("Image uploaded successfully to: " + imageUrl);
            } catch (Exception e) {
                logger.error("Error uploading image to Cloudinary: " + e.getMessage(), e);
                throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, 
                    "Error al cargar la imagen en Cloudinary: " + e.getMessage(),
                    e
                );
            }
        }
        
        try {
            CreateCareGuideCommand command = new CreateCareGuideCommand(
                resource.guideName(),
                resource.type(),
                resource.description(),
                imageUrl
            );
            
            logger.info("Creating care guide with command: " + command);
            Optional<CareGuide> careGuide = careGuideCommandService.handle(command);
            
            return careGuide.map(cg -> {
                logger.info("Care guide created successfully with ID: " + cg.getId());
                return new ResponseEntity<>(
                    CareGuideResourceFromEntityAssembler.toResourceFromEntity(cg), 
                    CREATED
                );
            }).orElseGet(() -> {
                logger.error("Failed to create care guide: No care guide returned from service");
                return ResponseEntity.badRequest().build();
            });
            
        } catch (Exception e) {
            logger.error("Error creating care guide: " + e.getMessage(), e);
            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR, 
                "Error al crear la guía de cuidados: " + e.getMessage(),
                e
            );
        }
        
    } catch (ResponseStatusException e) {
        throw e;
    } catch (Exception e) {
        logger.error("Unexpected error in createCareGuide: " + e.getMessage(), e);
        throw new ResponseStatusException(
            HttpStatus.INTERNAL_SERVER_ERROR, 
            "Error inesperado al procesar la solicitud: " + e.getMessage(),
            e
        );
    }
}
    /**
     * Retrieves a list of all care guides.
     * @return ResponseEntity with a list of care guide resources, or no content if there are no care guides
     * @since 1.0
     * @see CareGuideResource
     */
    @Operation(summary = "Get all care guides", description = "Retrieves a list of all care guides")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all care guides"),
            @ApiResponse(responseCode = "204", description = "No care guides found")
    })
    @GetMapping
    public ResponseEntity<List<CareGuideResource>>getAllCareGuides(){
        List<CareGuide> careGuides = careGuideQueryService.getAllCareGuide();
        if (careGuides.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<CareGuideResource> careGuideResources = careGuides.stream()
                .map(CareGuideResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(careGuideResources);
    }
    /**
     * Retrieves a specific care guide by its ID.
     * @param id The ID of the care guide to retrieve
     * @return ResponseEntity with the care guide resource, or not found if the care guide is not found
     * @since 1.0
     * @see CareGuideResource
     */
    @Operation(summary = "Get care guide by ID", description = "Retrieves a specific care guide by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the care guide"),
            @ApiResponse(responseCode = "404", description = "Care guide not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CareGuideResource> getCareGuideById(@PathVariable Long id) {
        return careGuideQueryService.getCareGuideById(id)
                .map(careGuide -> ResponseEntity.ok(CareGuideResourceFromEntityAssembler.toResourceFromEntity(careGuide)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    /**
     * Updates an existing care guide.
     * @param id The ID of the care guide to update
     * @param resource The CreateCareGuideResource containing the updated care guide details
     * @return ResponseEntity with the updated care guide resource, or not found if the care guide is not found
     * @since 1.0
     * @see CreateCareGuideResource
     * @see CareGuideResource
     */
    @Operation(summary = "Update an existing care guide", description = "Updates the details of an existing care guide")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the care guide"),
            @ApiResponse(responseCode = "404", description = "Care Guide not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CareGuideResource> updateCareGuide(
            @PathVariable Long id,
            @Valid @RequestBody CreateCareGuideResource resource) throws IOException {
        
        // Upload the image and get the URL
        String imageUrl = "";
        if (resource.image() != null && !resource.image().isEmpty()) {
            try {
                imageUrl = imageService.uploadImage(resource.image());
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload image", e);
            }
        }
        
        var updateCommand = new UpdateCareGuideCommand(
                id,
                resource.guideName(),
                resource.type(),
                resource.description(),
                imageUrl
        );

        return careGuideCommandService.handle(updateCommand)
                .map(updateCareGuide -> ResponseEntity.ok(
                        CareGuideResourceFromEntityAssembler.toResourceFromEntity(updateCareGuide)
                ))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
    /**
     * Deletes a specific care guide by its ID.
     * @param id The ID of the care guide to delete
     * @return ResponseEntity with no content, or not found if the care guide is not found
     * @since 1.0
     */
    @Operation(summary = "Delete a care guide", description = "Deletes a specific care guide by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the care guide"),
            @ApiResponse(responseCode = "404", description = "Care guide not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCareGuide(@PathVariable Long id) {
        careGuideCommandService.handle(new DeleteCareGuideCommand(id));
        return ResponseEntity.noContent().build();
    }
}
