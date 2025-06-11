package com.stocksip.analyticsreporting.interfaces.rest;

import com.stocksip.analyticsreporting.domain.model.aggregates.CareGuide;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
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
    private final CareGuideCommandService careGuideCommandService;
    private final CareGuideQueryService careGuideQueryService;
    
    /**
     * Constructor for CareGuideController.
     * @param careGuideCommandService CareGuide command service
     * @param careGuideQueryService CareGuide query service
     * @since 1.0
     * @see CareGuideCommandService
     * @see CareGuideQueryService
     */
    public CareGuideController(CareGuideCommandService careGuideCommandService, CareGuideQueryService careGuideQueryService) {
        this.careGuideCommandService = careGuideCommandService;
        this.careGuideQueryService = careGuideQueryService;
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
            description = "Creates a new care guide based on the provided details in the request body.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Care Guide created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request")
            }
    )
    @PostMapping
    public ResponseEntity<CareGuideResource> createCareGuide(@RequestBody CreateCareGuideResource resource){
        Optional<CareGuide>careGuide= careGuideCommandService.handle(CreateCareGuideCommandFromResourceAssembler.toCommandFromResource(resource));
        return careGuide.map(careGuide1 -> new ResponseEntity<>(CareGuideResourceFromEntityAssembler.toResourceFromEntity(careGuide1), CREATED))
                .orElseGet(() -> ResponseEntity.badRequest().build());
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
            @Valid @RequestBody CreateCareGuideResource resource){
        var updateCommand = new UpdateCareGuideCommand(
                id,
                resource.guideName(),
                resource.type(),
                resource.description()
        );

        return careGuideCommandService.handle(updateCommand).map(updateCareGuide->ResponseEntity.ok(
                CareGuideResourceFromEntityAssembler.toResourceFromEntity(updateCareGuide)
        )).orElseGet(()->ResponseEntity.badRequest().build());
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
