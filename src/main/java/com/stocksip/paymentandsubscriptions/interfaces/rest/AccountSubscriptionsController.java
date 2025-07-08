package com.stocksip.paymentandsubscriptions.interfaces.rest;

import com.stocksip.paymentandsubscriptions.domain.model.queries.GetPlanTypeByAccountIdQuery;
import com.stocksip.paymentandsubscriptions.domain.services.SubscriptionQueryService;
import com.stocksip.paymentandsubscriptions.interfaces.rest.resources.CurrentPlanResource;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "api/v1/accounts/{accountId}/subscriptions", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Accounts")
public class AccountSubscriptionsController {

    private final SubscriptionQueryService subscriptionQueryService;

    public AccountSubscriptionsController(SubscriptionQueryService subscriptionQueryService) {
        this.subscriptionQueryService = subscriptionQueryService;
    }

    @GetMapping("current-plan")
    public ResponseEntity<?> getCurrentPlan(@PathVariable Long accountId) {
        var currentPlan = new GetPlanTypeByAccountIdQuery(accountId);
        Optional<String> planType = subscriptionQueryService.handle(currentPlan);
        return ResponseEntity.ok(planType);
    }
}
