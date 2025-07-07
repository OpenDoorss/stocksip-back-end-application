package com.stocksip.paymentandsubscriptions.interfaces.rest;

import com.stocksip.inventorymanagement.domain.model.queries.GetWarehouseByIdQuery;
import com.stocksip.paymentandsubscriptions.domain.model.queries.GetAllPlansQuery;
import com.stocksip.paymentandsubscriptions.domain.services.PlanQueryService;
import com.stocksip.paymentandsubscriptions.interfaces.rest.resources.PlanResource;
import com.stocksip.paymentandsubscriptions.interfaces.rest.transform.PlansResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*
    * REST controller for Plans.
    * @summary
    * This class provides REST endpoints for plans.
    * @since 1.0.0
 */
@RestController
@RequestMapping(value = "api/v1/plans", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "plans", description = "Available endpoints for plans.")
public class PlansController {

    private final PlanQueryService planQueryService;

    public PlansController(PlanQueryService planQueryService) {
        this.planQueryService = planQueryService;
    }

    @GetMapping
    @Operation(summary = "Get all plans",
            description = "Retrieves a list of all available plans.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all plans"),
            @ApiResponse(responseCode = "404", description = "No plans found")
    })
    public ResponseEntity<List<PlanResource>> getAllPlans() {
        var getAllPlansQuery = new GetAllPlansQuery();
        var plans = planQueryService.handle(getAllPlansQuery);
        return ResponseEntity.ok(plans.stream().map(PlansResourceFromEntityAssembler::toResourceFromEntity).toList());
    }
}
