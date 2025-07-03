package com.stocksip.inventorymanagement.interfaces.rest;

import com.stocksip.inventorymanagement.domain.model.queries.GetAllProductsByWarehouseIdQuery;
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
 * REST controller for Warehouse Products.
 * @summary
 * This class provides REST endpoints for warehouse products.
 * @since 1.0.0
 */
@RestController
@RequestMapping(value = "api/v1/warehouses/{warehouseId}/products", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Warehouses")
public class WarehouseProductsController {

    private final ProductQueryService productQueryService;

    public WarehouseProductsController(ProductQueryService productQueryService) {
        this.productQueryService = productQueryService;
    }
}
