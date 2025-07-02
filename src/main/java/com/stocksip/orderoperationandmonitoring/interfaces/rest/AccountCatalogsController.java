package com.stocksip.orderoperationandmonitoring.interfaces.rest;

import com.stocksip.orderoperationandmonitoring.domain.model.aggregates.Catalog;
import com.stocksip.orderoperationandmonitoring.domain.model.aggregates.CatalogItem;
import com.stocksip.orderoperationandmonitoring.domain.model.queries.*;
import com.stocksip.orderoperationandmonitoring.domain.model.valueobjects.AccountId;
import com.stocksip.orderoperationandmonitoring.domain.services.CatalogQueryService;
import com.stocksip.orderoperationandmonitoring.interfaces.rest.resources.CatalogItemResource;
import com.stocksip.orderoperationandmonitoring.interfaces.rest.resources.CatalogResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(
        value = "api/v1/accounts/{accountId}/catalogs",
        produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Accounts")
public class AccountCatalogsController {

    private final CatalogQueryService queryService;

    public AccountCatalogsController(CatalogQueryService queryService) {
        this.queryService = queryService;
    }

    @GetMapping
    @Operation(summary = "Get all published catalogs by account ID",
            description = "Retrieves all published catalogs associated with a specific account ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Catalogs retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Not found - invalid account ID")
    })
    public ResponseEntity<List<CatalogResource>> getCatalogs(
            @RequestParam(required = false) Long    accountIdParam,
            @RequestParam(name = "published", required = false) Boolean published) {

        AccountId accountId = accountIdParam != null ? new AccountId(accountIdParam) : null;

        List<Catalog> catalogs = (published != null && published)
                ? queryService.handle(new GetPublishedCatalogsByAccountIdQuery(accountId, true))
                : queryService.handle(new GetCatalogsByAccountQuery(accountId));

        if (catalogs.isEmpty()) return ResponseEntity.notFound().build();

        List<CatalogResource> resources = catalogs.stream()
                .map(this::toResource)
                .toList();

        return ResponseEntity.ok(resources);
    }



    private CatalogResource toResource(Catalog catalog) {
        return new CatalogResource(
                catalog.getCatalogId(),
                catalog.getAccountId().accountId(),
                catalog.getName().value(),
                catalog.getDateCreated().value(),
                catalog.isPublished()
        );
    }

    private CatalogItemResource toResource(CatalogItem item) {
        return new CatalogItemResource(
                item.getId(),
                item.getCatalog().getCatalogId(),
                item.getName().value(),
                item.getProductType().value(),
                item.getBrand().value(),
                item.getContent().value(),
                item.getUnitPrice(),
                item.getDateAdded()
        );
    }
}
