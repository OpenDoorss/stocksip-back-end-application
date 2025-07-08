package com.stocksip.paymentandsubscriptions.interfaces.rest.resources;

/**
 * This resource is used to subscribe an account to a plan.
 * @param accountId - the ID of the account to subscribe
 * @param planId - the ID of the plan to subscribe to
 */
public record SubscribeToPlanResource(Long accountId, Long planId) {}
