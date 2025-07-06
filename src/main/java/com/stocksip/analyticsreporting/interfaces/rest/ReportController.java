package com.stocksip.analyticsreporting.interfaces.rest;

import com.stocksip.analyticsreporting.domain.model.aggregates.Report;
import com.stocksip.analyticsreporting.domain.model.commands.DeleteReportCommand;
import com.stocksip.analyticsreporting.domain.model.commands.UpdateReportCommand;
import com.stocksip.analyticsreporting.domain.services.ReportCommandService;
import com.stocksip.analyticsreporting.domain.services.ReportQueryService;
import com.stocksip.analyticsreporting.interfaces.rest.resources.CreateReportResource;
import com.stocksip.analyticsreporting.interfaces.rest.resources.ReportResource;
import com.stocksip.analyticsreporting.interfaces.rest.transform.CreateReportCommandFromResourceAssembler;
import com.stocksip.analyticsreporting.interfaces.rest.transform.ReportResourceFromEntityAssembler;
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
 * REST controller for Report.
 * @summary
 * This class provides REST endpoints for reports.
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/api/v1/report", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Report", description = "Endpoints for reports")
public class ReportController {
    private final ReportCommandService reportCommandService;
    private final ReportQueryService reportQueryService;

    /**
     * Constructor for ReportController.
     * @param reportCommandService Report command service
     * @param reportQueryService Report query service
     * @since 1.0
     * @see ReportCommandService
     * @see ReportQueryService
     */
    public ReportController(ReportCommandService reportCommandService, ReportQueryService reportQueryService) {
        this.reportCommandService = reportCommandService;
        this.reportQueryService = reportQueryService;
    }
    /**
     * Creates a report.
     * @param resource CreateReportResource containing the attributes of the report to be created
     * @return ResponseEntity with the created report resource, or bad request if the resource is invalid
     * @since 1.0
     * @see CreateFavoriteSourceResource
     * @see FavoriteSourceResource
     */
    @Operation(
            summary = "Create a new report",
            description = "Creates a new report based on the provided details in the request body.")
    @ApiResponses(
            value={
                    @ApiResponse(responseCode = "201", description = "Report created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request")
            }
    )
    @PostMapping
    public ResponseEntity<ReportResource>createReport(@RequestBody CreateReportResource resource) {
        Optional<Report> report=reportCommandService.handle(CreateReportCommandFromResourceAssembler.toCommandFromResource(resource));
        return report.map(reporting -> new ResponseEntity<>(ReportResourceFromEntityAssembler.toResourceFromEntity(reporting),CREATED)).
                orElseGet(()->ResponseEntity.badRequest().build());
    }
    /**
     * Retrieves a list of all reports.
     * @return ResponseEntity with a list of report resources, or no content if there are no reports
     * @since 1.0
     * @see ReportResource
     */
    @Operation(summary = "Get all reports", description = "Retrieves a list of all reports")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all reports"),
            @ApiResponse(responseCode = "204", description = "No reports found")
    })
    @GetMapping
    public ResponseEntity<List<ReportResource>> getAllReports() {
        List<Report> reports = reportQueryService.getAllReports();
        if (reports.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<ReportResource> reportResources = reports.stream()
                .map(ReportResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reportResources);
    }
    /**
     * Retrieves a specific report by its ID.
     * @param id The ID of the report to retrieve
     * @return ResponseEntity with the report resource, or not found if the report is not found
     * @since 1.0
     * @see ReportResource
     */
    @Operation(summary = "Get report by ID", description = "Retrieves a specific report by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the report"),
            @ApiResponse(responseCode = "404", description = "Report not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ReportResource> getReportById(@PathVariable Long id) {
        return reportQueryService.getReportById(id)
                .map(report -> ResponseEntity.ok(ReportResourceFromEntityAssembler.toResourceFromEntity(report)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    /**
     * Updates an existing report.
     * @param id The ID of the report to update
     * @param resource The CreateReportResource containing the updated report details
     * @return ResponseEntity with the updated report resource, or bad request if the report is not found or the input is invalid
     * @since 1.0
     * @see ReportResource
     */
    @Operation(summary = "Update an existing report", description = "Updates the details of an existing report")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the report"),
            @ApiResponse(responseCode = "404", description = "Report not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ReportResource> updateReport(
            @PathVariable Long id,
            @Valid @RequestBody CreateReportResource resource) {
        var updateCommand = new UpdateReportCommand(
                id,
                resource.productName(),
                resource.type(),
                resource.price(),
                resource.amount(),
                resource.reportDate(),
                resource.lostAmount()
        );
        
        return reportCommandService.handle(updateCommand)
                .map(updatedReport -> ResponseEntity.ok(ReportResourceFromEntityAssembler.toResourceFromEntity(updatedReport)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    /**
     * Deletes a specific report by its ID.
     * @param id The ID of the report to delete
     * @return ResponseEntity with no content, or not found if the report is not found
     * @since 1.0
     */
    @Operation(summary = "Delete a report", description = "Deletes a specific report by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the report"),
            @ApiResponse(responseCode = "404", description = "Report not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable Long id) {
        reportCommandService.handle(new DeleteReportCommand(id));
        return ResponseEntity.noContent().build();
    }
}
