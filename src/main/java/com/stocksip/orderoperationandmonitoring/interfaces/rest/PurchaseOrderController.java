package com.stocksip.orderoperationandmonitoring.interfaces.rest;

import com.stocksip.orderoperationandmonitoring.domain.model.aggregates.PurchaseOrder;
import com.stocksip.orderoperationandmonitoring.domain.model.commands.CreateOrderCommand;
import com.stocksip.orderoperationandmonitoring.domain.model.valueobjects.OrderStatus;
import com.stocksip.orderoperationandmonitoring.domain.services.PurchaseOrderCommandService;
import com.stocksip.orderoperationandmonitoring.domain.services.PurchaseOrderQueryService;
import com.stocksip.orderoperationandmonitoring.interfaces.rest.resources.CreateOrderResource;
import com.stocksip.orderoperationandmonitoring.interfaces.rest.resources.PurchaseOrderResource;
import com.stocksip.orderoperationandmonitoring.interfaces.rest.transform.CreateOrderCommandFromResourceAssembler;
import com.stocksip.orderoperationandmonitoring.interfaces.rest.transform.PurchaseOrderResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/orders", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Orders", description = "Order management endpoints")
@CrossOrigin(origins = "http://localhost:4200")
public class PurchaseOrderController {

    private final PurchaseOrderCommandService commandService;
    private final PurchaseOrderQueryService queryService;

    public PurchaseOrderController(PurchaseOrderCommandService commandService,
                                   PurchaseOrderQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    /* ────────────────────────────────
     * COMMAND – CREATE ORDER
     * ──────────────────────────────── */
    @PostMapping
    public ResponseEntity<PurchaseOrderResource> createOrder(@RequestBody CreateOrderResource resource) {
        CreateOrderCommand cmd = CreateOrderCommandFromResourceAssembler.toCommand(resource);
        PurchaseOrder created = commandService.handle(cmd).orElseThrow();

        URI location = URI.create("/api/v1/orders/" + created.getId());
        PurchaseOrderResource resourceResp = PurchaseOrderResourceFromEntityAssembler.toResource(created);

        return ResponseEntity.created(location).body(resourceResp);
    }

    /* ────────────────────────────────
     * COMMAND – UPDATE STATUS
     * ──────────────────────────────── */
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateOrderStatus(@PathVariable Long id, @RequestBody StatusUpdateRequest request) {
        commandService.changeStatus(id, OrderStatus.valueOf(request.status()));
        return ResponseEntity.noContent().build();
    }

    public record StatusUpdateRequest(String status) {}

    /* ────────────────────────────────
     * QUERY – GET ALL ORDERS
     * ──────────────────────────────── */
    @GetMapping
    public ResponseEntity<List<PurchaseOrderResource>> getAllOrders() {
        List<PurchaseOrder> orders = queryService.findAll();
        List<PurchaseOrderResource> resources = orders.stream()
                .map(PurchaseOrderResourceFromEntityAssembler::toResource)
                .toList();
        return ResponseEntity.ok(resources);
    }

    /* ────────────────────────────────
     * QUERY – GET BY BUYER
     * ──────────────────────────────── */
    @GetMapping("/buyer/{buyerId}")
    public ResponseEntity<List<PurchaseOrderResource>> getOrdersByBuyer(@PathVariable Long buyerId) {
        List<PurchaseOrder> orders = queryService.findByBuyerAccountId(buyerId);
        return ResponseEntity.ok(orders.stream()
                .map(PurchaseOrderResourceFromEntityAssembler::toResource)
                .toList());
    }

    /* ────────────────────────────────
     * QUERY – GET BY SUPPLIER
     * ──────────────────────────────── */
    @GetMapping("/supplier/{supplierId}")
    public ResponseEntity<List<PurchaseOrderResource>> getOrdersBySupplier(@PathVariable Long supplierId) {
        List<PurchaseOrder> orders = queryService.findBySupplierAccountId(supplierId);
        return ResponseEntity.ok(orders.stream()
                .map(PurchaseOrderResourceFromEntityAssembler::toResource)
                .toList());
    }
}
