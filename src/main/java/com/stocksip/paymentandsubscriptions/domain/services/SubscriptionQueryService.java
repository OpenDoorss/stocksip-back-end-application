package com.stocksip.paymentandsubscriptions.domain.services;

import com.stocksip.paymentandsubscriptions.domain.model.queries.GetPlanIdByAccountIdQuery;

import java.util.Optional;

/**
 * This service is responsible for handling subscription-related queries.
 */
public interface SubscriptionQueryService {

    /*
    * This method retrieves the plan ID associated with a specific account ID.
    */
    Optional<Long> handle(GetPlanIdByAccountIdQuery query);
}
