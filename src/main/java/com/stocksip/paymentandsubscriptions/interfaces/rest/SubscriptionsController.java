package com.stocksip.paymentandsubscriptions.interfaces.rest;

import com.stocksip.paymentandsubscriptions.domain.model.commands.CompleteSubscriptionCommand;
import com.stocksip.paymentandsubscriptions.domain.model.commands.UpgradeSubscriptionCommand;
import com.stocksip.paymentandsubscriptions.domain.services.SubscriptionCommandService;
import com.stocksip.paymentandsubscriptions.interfaces.rest.resources.CompleteSubscriptionResource;
import com.stocksip.paymentandsubscriptions.interfaces.rest.resources.SubscribeToPlanResource;
import com.stocksip.paymentandsubscriptions.interfaces.rest.resources.UpgradeSubscriptionResource;
import com.stocksip.paymentandsubscriptions.interfaces.rest.transform.CompleteSubscriptionFromResourceAssembler;
import com.stocksip.paymentandsubscriptions.interfaces.rest.transform.SubscribeToPlanFromResourceAssembler;
import com.stocksip.paymentandsubscriptions.interfaces.rest.transform.UpgradeSubscriptionFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

/**
 * This controller handles subscription-related operations such as subscribing to a plan and completing a subscription.
 */
@RestController
@RequestMapping(value = "api/v1/subscriptions", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Subscriptions", description = "Available Subscriptions Endpoints.")
public class SubscriptionsController {

    private final SubscriptionCommandService subscriptionCommandService;

    public SubscriptionsController(SubscriptionCommandService subscriptionCommandService) {
        this.subscriptionCommandService = subscriptionCommandService;
    }

    @PostMapping("new")
    @Operation(summary = "Subscribe to a plan",
            description = "Subscribe to a plan with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subscription created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public ResponseEntity<Map<String, String>> subscribe(@RequestBody SubscribeToPlanResource resource) {
        Optional<String> result = subscriptionCommandService.handle(
                SubscribeToPlanFromResourceAssembler.toCommandFromResource(resource)
        );

        return result
                .map(url -> ResponseEntity.ok(Map.of("redirectUrl", url)))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("complete")
    @Operation(summary = "Complete a subscription",
            description = "Complete a subscription with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subscription completed successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public ResponseEntity<Map<String, String>> completeSubscription(@ModelAttribute CompleteSubscriptionResource resource) {
        CompleteSubscriptionCommand command = CompleteSubscriptionFromResourceAssembler.toCommandFromResource(resource);
        subscriptionCommandService.handle(command);
        return ResponseEntity.ok(Map.of("message", "Subscription completed successfully"));
    }

    @PostMapping("upgrade")
    @Operation(summary = "Upgrade a subscription",
            description = "Upgrade an existing subscription to a new plan.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subscription upgraded successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public ResponseEntity<Map<String, String>> Upgrade(@RequestBody UpgradeSubscriptionResource resource) {
        Optional<String> result = subscriptionCommandService
                .handle(UpgradeSubscriptionFromResourceAssembler.toCommandFromResource(resource));

        return result
                .map(url -> ResponseEntity.ok(Map.of("redirectUrl", url)))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("upgrade-complete")
    @Operation(summary = "Complete an upgrade",
            description = "Complete the upgrade of a subscription with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subscription upgrade completed successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public ResponseEntity<Map<String, String>> UpgradeSuccess(@ModelAttribute UpgradeSubscriptionResource resource) {
        UpgradeSubscriptionCommand command = UpgradeSubscriptionFromResourceAssembler.toCommandFromResource(resource);
        subscriptionCommandService.handle(command);
        return ResponseEntity.ok(Map.of("message", "Subscription upgraded successfully"));
    }
}
