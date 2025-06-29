package com.stocksip.inventorymanagement.interfaces.rest;

import com.stocksip.inventorymanagement.domain.model.aggregates.Product;
import com.stocksip.inventorymanagement.domain.model.commands.DeleteProductCommand;
import com.stocksip.inventorymanagement.domain.model.commands.UpdateProductCommand;
import com.stocksip.inventorymanagement.domain.model.queries.GetProductByIdQuery;
import com.stocksip.inventorymanagement.domain.services.ProductCommandService;
import com.stocksip.inventorymanagement.domain.services.ProductQueryService;
import com.stocksip.inventorymanagement.interfaces.rest.resources.CreateProductResource;
import com.stocksip.inventorymanagement.interfaces.rest.resources.ProductResource;
import com.stocksip.inventorymanagement.interfaces.rest.resources.UpdateProductResource;
import com.stocksip.inventorymanagement.interfaces.rest.transform.CreateProductCommandFromResourceAssembler;
import com.stocksip.inventorymanagement.interfaces.rest.transform.ProductResourceFromEntityAssembler;
import com.stocksip.inventorymanagement.interfaces.rest.transform.UpdateProductCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;

/**
 * REST controller for Products.
 * @summary
 * This class provides REST endpoints for products.
 * @since 1.0.0
 */
@RestController
@RequestMapping(value = "api/v1/products", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Products", description = "Endpoints for managing products.")
public class ProductsController {

    private final ProductCommandService productCommandService;
    private final ProductQueryService productQueryService;

    public ProductsController(ProductCommandService productCommandService, ProductQueryService productQueryService) {
        this.productCommandService = productCommandService;
        this.productQueryService = productQueryService;
    }

    @GetMapping("{productId}")
    @Operation(summary = "Get a product by ID",
            description = "Retrieves the details of a product identified by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found - invalid product ID")
    })
    public ResponseEntity<ProductResource> getProductById(@PathVariable Long productId) {

        var getProductByIdQuery = new GetProductByIdQuery(productId);
        var product = productQueryService.handle(getProductByIdQuery);
        if (product.isEmpty()) return ResponseEntity.notFound().build();
        var productEntity = product.get();
        var productResource = ProductResourceFromEntityAssembler.toResourceFromEntity(productEntity);
        return ResponseEntity.ok(productResource);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Create a new product",
            description = "Creates a new product with the provided details."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public ResponseEntity<ProductResource> createProduct(@ModelAttribute CreateProductResource resource) {
        Optional<Product> product = productCommandService.handle(CreateProductCommandFromResourceAssembler.toCommandFromResource(resource));

        return product.map(source ->
                new ResponseEntity<>(ProductResourceFromEntityAssembler.toResourceFromEntity(source), CREATED))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PutMapping(value = "/{productId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Update an existing product",
            description = "Updates the details of an existing product identified by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully"),
            @ApiResponse(responseCode = "400", description = "Product could not be updated - invalid product ID or resource")
    })
    public ResponseEntity<ProductResource> updateProduct(@ModelAttribute UpdateProductResource resource, @PathVariable Long productId) {
        var updateProductCommand = UpdateProductCommandFromResourceAssembler.toCommandFromResource(resource, productId);
        var updatedProduct = productCommandService.handle(updateProductCommand);
        if (updatedProduct.isEmpty()) return ResponseEntity.badRequest().build();
        var updatedProductEntity = updatedProduct.get();
        var updatedProductResource = ProductResourceFromEntityAssembler.toResourceFromEntity(updatedProductEntity);
        return ResponseEntity.ok(updatedProductResource);
    }

    @DeleteMapping("/{productId}")
    @Operation(summary = "Delete an existing product",
            description = "Deletes a product without stock identified by its ID in any of the warehouses.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Product could not be deleted - invalid product ID")
    })
    public ResponseEntity<Map<String, String>> deleteProduct(@PathVariable Long productId) {
        var deleteProductCommand = new DeleteProductCommand(productId);
        productCommandService.handle(deleteProductCommand);
        return ResponseEntity.ok(Map.of("message", "Product with given Id deleted successfully."));
    }
}
