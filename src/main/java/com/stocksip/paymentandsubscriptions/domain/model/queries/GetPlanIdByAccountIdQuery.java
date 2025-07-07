package com.stocksip.paymentandsubscriptions.domain.model.queries;

/**
 * This query is used to retrieve the plan ID associated with a specific account ID.
 * @param accountId
 */
public record GetPlanIdByAccountIdQuery(Long accountId) {}
