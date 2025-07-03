package com.stocksip.orderoperationandmonitoring.interfaces.rest;

import com.stocksip.orderoperationandmonitoring.domain.model.aggregates.Catalog;
import com.stocksip.orderoperationandmonitoring.domain.model.aggregates.CatalogItem;
import com.stocksip.orderoperationandmonitoring.domain.model.commands.*;
import com.stocksip.orderoperationandmonitoring.domain.model.queries.*;
import com.stocksip.orderoperationandmonitoring.domain.model.valueobjects.AccountId;
import com.stocksip.orderoperationandmonitoring.domain.services.CatalogCommandService;
import com.stocksip.orderoperationandmonitoring.domain.services.CatalogQueryService;
import com.stocksip.orderoperationandmonitoring.interfaces.rest.resources.CatalogItemResource;
import com.stocksip.orderoperationandmonitoring.interfaces.rest.resources.CatalogResource;
import com.stocksip.orderoperationandmonitoring.interfaces.rest.transform.*;
import io.micrometer.common.util.internal.logging.InternalLogger;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Catalog")
@CrossOrigin(origins = "http://localhost:4200")
public class CatalogController {

    private final CatalogCommandService catalogCommandService;
    private final CatalogQueryService catalogQueryService;

    public CatalogController(CatalogCommandService catalogCommandService,
                             CatalogQueryService catalogQueryService) {
        this.catalogCommandService = catalogCommandService;
        this.catalogQueryService = catalogQueryService;
    }

    /* ────────────────────────────────
     * QUERIES – CATALOGS
     * ──────────────────────────────── */
    @GetMapping("/catalogs")
    @Operation(summary = "Get catalogs", description = "Filter by accountId and/or isPublished; both params optional.")
    public ResponseEntity<List<CatalogResource>> getCatalogs(
            @RequestParam(required = false) Long accountIdParam,
            @RequestParam(required = false) Boolean isPublished) {

        AccountId accountId = accountIdParam != null ? new AccountId(accountIdParam) : null;

        List<Catalog> catalogs = switch ((accountId != null ? 1 : 0)
                + (Boolean.TRUE.equals(isPublished) ? 2 : 0)) {
            case 3 -> catalogQueryService.handle(new GetPublishedCatalogsByAccountIdQuery(accountId, true));
            case 2 -> catalogQueryService.handle(new GetPublishedCatalogsQuery(true));
            case 1 -> catalogQueryService.handle(new GetCatalogsByAccountQuery(accountId));
            default -> catalogQueryService.handle(new GetAllCatalogsQuery());
        };

        if (catalogs.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(catalogs.stream().map(this::toResource).toList());
    }

    @GetMapping("/catalogs/{id}")
    @Operation(summary = "Get catalog by ID")
    public ResponseEntity<CatalogResource> getCatalogById(@PathVariable Long id) {
        return catalogQueryService.handle(new GetCatalogByIdQuery(id))
                .map(this::toResource)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /* ────────────────────────────────
     * COMMANDS – CATALOGS
     * ──────────────────────────────── */
    @PostMapping("/catalogs")
    @Operation(summary = "Create catalog")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Catalog created"),
            @ApiResponse(responseCode = "400", description = "Validation error")
    })
    public ResponseEntity<CatalogResource> createCatalog(@RequestBody CatalogResource resource) {
        var cmd = CreateCatalogCommandFromResourceAssembler.toCommandFromResource(resource);
        var created = catalogCommandService.handle(cmd).orElseThrow();

        URI location = URI.create("/api/v1/catalogs/" + created.getCatalogId());
        return ResponseEntity.created(location).body(toResource(created));
    }

    @PutMapping("/catalogs/{catalogId}")
    @Operation(summary = "Update catalog (name only)")
    public ResponseEntity<CatalogResource> updateCatalog(@PathVariable Long catalogId,
                                                         @RequestBody CatalogResource resource) {
        var cmd = UpdateCatalogCommandFromResourceAssembler.toCommandFromResource(catalogId, resource);
        var updated = catalogCommandService.handle(cmd).orElseThrow();
        return ResponseEntity.ok(toResource(updated));
    }

    @PostMapping("/catalogs/{id}/publish")
    @Operation(summary = "Publish catalog")
    public ResponseEntity<CatalogResource> publishCatalog(@PathVariable Long id) {
        var published = catalogCommandService.handle(new PublishCatalogCommand(id)).orElseThrow();
        return ResponseEntity.ok(toResource(published));
    }


    @GetMapping("/catalogs/published")
    public ResponseEntity<List<CatalogResource>> publishedByProviderEmail(
            @RequestParam String providerEmail) {

        try {
            List<Catalog> catalogs =
                    catalogQueryService.getPublishedCatalogsByProviderEmail(providerEmail);

            List<CatalogResource> resources = catalogs.stream()
                    .map(CatalogResourceFromEntityAssembler::toResourceFromEntity)
                    .toList();

            return ResponseEntity.ok(resources);

        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(List.of());
        }
    }

    @GetMapping("/catalogItems")
    @Operation(summary = "Get catalog items")
    public ResponseEntity<List<CatalogItemResource>> getCatalogItems(@RequestParam Long catalogId) {
        var items = catalogQueryService.handle(new GetCatalogItemsByCatalogIdQuery(catalogId));
        if (items.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(items.stream().map(this::toResource).toList());
    }

    @PostMapping("/catalogItems")
    @Operation(summary = "Create catalog item")
    public ResponseEntity<CatalogItemResource> createCatalogItem(@RequestBody CatalogItemResource resource) {
        var command = CreateCatalogItemCommandFromResourceAssembler
                .toCommandFromResource(resource, resource.catalogId());

        var created = catalogCommandService.handle(command).orElseThrow();

        URI location = URI.create("/api/v1/catalogItems/" + created.getId());
        return ResponseEntity.created(location).body(toResource(created));
    }

    @DeleteMapping("/catalogItems/{id}")
    @Operation(summary = "Delete catalog item")
    public ResponseEntity<Void> deleteCatalogItem(@PathVariable String id) {
        var cmd = DeleteCatalogItemCommandFromIdAssembler.toCommandFromId(id);
        catalogCommandService.handle(cmd);
        return ResponseEntity.noContent().build();
    }

    /* ────────────────────────────────
     * MAPPERS
     * ──────────────────────────────── */
    private CatalogResource toResource(Catalog c) {
        return new CatalogResource(
                c.getCatalogId(),
                c.getAccountId().accountId(),
                c.getName().value(),
                c.getDateCreated().value(),
                c.isPublished()
        );
    }

    private CatalogItemResource toResource(CatalogItem i) {
        return new CatalogItemResource(
                i.getId(),
                i.getCatalog().getCatalogId(),
                i.getName().value(),
                i.getProductType().value(),
                i.getBrand().value(),
                i.getContent().value(),
                i.getUnitPrice(),
                i.getDateAdded()
        );
    }
}
