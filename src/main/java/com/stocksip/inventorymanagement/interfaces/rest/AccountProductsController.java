package com.stocksip.inventorymanagement.interfaces.rest;

import com.stocksip.inventorymanagement.domain.model.queries.GetAllProductsByAccountIdQuery;
import com.stocksip.inventorymanagement.domain.model.valueobjects.AccountId;
import com.stocksip.inventorymanagement.domain.services.ProductQueryService;
import com.stocksip.inventorymanagement.interfaces.rest.resources.ProductResource;
import com.stocksip.inventorymanagement.interfaces.rest.transform.ProductResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for Account Products.
 * @summary
 * This class provides REST endpoints for account products.
 * @since 1.0.0
 */
@RestController
@RequestMapping(value = "api/v1/accounts/{accountId}/products", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Accounts")
public class AccountProductsController {

    private final ProductQueryService productQueryService;

    public AccountProductsController(ProductQueryService productQueryService) {
        this.productQueryService = productQueryService;
    }

    @GetMapping
    @Operation(summary = "Get all products by account ID",
            description = "Retrieves all products associated with a specific account ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request - invalid account ID")
    })
    public ResponseEntity<List<ProductResource>> getAllProductsByAccountId(@PathVariable Long accountId) {
        var targetAccountId = new AccountId(accountId);
        var getAllProductsByAccountIdQuery = new GetAllProductsByAccountIdQuery(targetAccountId);
        var products = productQueryService.handle(getAllProductsByAccountIdQuery);
        if (products.isEmpty()) return ResponseEntity.notFound().build();
        var productEntities = products.stream().map(ProductResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(productEntities);
    }
}
