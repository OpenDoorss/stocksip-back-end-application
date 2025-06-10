package com.stocksip.analyticsreporting.interfaces.rest;

import com.stocksip.analyticsreporting.domain.model.aggregates.CareGuide;
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

    public CareGuideController(CareGuideCommandService careGuideCommandService, CareGuideQueryService careGuideQueryService) {
        this.careGuideCommandService = careGuideCommandService;
        this.careGuideQueryService = careGuideQueryService;
    }

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

    @Operation(summary = "Update an existing care guide", description = "Updates the details of an existing care guide")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the care guide"),
            @ApiResponse(responseCode = "404", description = "Care Guide not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CareGuideResource> updateCareGuide(
            @PathVariable Long id,
            @RequestBody CreateCareGuideResource resource){
        return careGuideCommandService.updateCareGuide(id, CreateCareGuideCommandFromResourceAssembler.toCommandFromResource(resource))
                .map(updatedCareGuide -> ResponseEntity.ok(CareGuideResourceFromEntityAssembler.toResourceFromEntity(updatedCareGuide)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @Operation(summary = "Delete a care guide", description = "Deletes a specific care guide by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the care guide"),
            @ApiResponse(responseCode = "404", description = "Care guide not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCareGuide(@PathVariable Long id) {
        boolean deleted = careGuideCommandService.deleteCareGuide(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
