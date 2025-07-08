package com.stocksip.paymentandsubscriptions.interfaces.rest.resources;

/**
 * This resource is used to complete a subscription for an account.
 * @param token - the token to complete the subscription
 * @param accountId - the ID of the account to complete the subscription for
 * @param planId - the ID of the plan to complete the subscription for
 */
public record CompleteSubscriptionResource(String token, Long accountId, Long planId) {}
