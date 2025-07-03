package com.stocksip.alertsandnotifications.interfaces.rest;

import com.stocksip.alertsandnotifications.domain.model.queries.GetAllAlertsByProfileIdQuery;
import com.stocksip.alertsandnotifications.domain.model.valueobjects.ProfileId;
import com.stocksip.alertsandnotifications.domain.services.AlertQueryService;
import com.stocksip.alertsandnotifications.interfaces.rest.resources.AlertResource;
import com.stocksip.alertsandnotifications.interfaces.rest.transform.AlertResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for managing alerts associated with specific profiles.
 *
 * @summary
 * REST controller that exposes endpoints for profile-specific alert operations,
 * allowing retrieval of alerts by profile ID.
 *
 * @since 1.0
 */
@RestController
@RequestMapping(value = "api/v1/profiles/{profileId}/alerts", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Profiles", description = "Profile-specific alert operations")
public class ProfileAlertsController {

    private final AlertQueryService alertQueryService;

    /**
     * Constructor for ProfileAlertsController.
     *
     * @param alertQueryService The query service for retrieving alert information.
     */
    public ProfileAlertsController(AlertQueryService alertQueryService) {
        this.alertQueryService = alertQueryService;
    }

    /**
     * This endpoint retrieves all alerts associated with a specific profile ID.
     *
     * @param profileId The profile ID for which alerts are being requested.
     * @return The ResponseEntity containing the list of alert resources, or a NotFound result if no alerts found.
     */
    @Operation(
            summary = "Get all alerts by profile ID",
            description = "Retrieves all alerts associated with a specific profile ID.",
            operationId = "GetAlertsByProfileId"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns all alerts by profile ID.", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = AlertResource.class))),
            @ApiResponse(responseCode = "404", description = "No alerts found for the specified profile.")
    })
    @GetMapping
    public ResponseEntity<?> getAlertsByProfileId(@PathVariable String profileId) {
        var targetProfileId = new ProfileId(profileId);
        var getAllAlertsByProfileIdQuery = new GetAllAlertsByProfileIdQuery(targetProfileId);
        var alerts = alertQueryService.handle(getAllAlertsByProfileIdQuery);
        
        if (alerts.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        var alertResources = alerts.stream()
                .map(AlertResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        
        return ResponseEntity.ok(alertResources);
    }
} 