package com.stocksip.paymentandsubscriptions.interfaces.rest.resources;

/**
 * This resource is used to upgrade a subscription plan.
 * @param accountId - the ID of the account to upgrade
 * @param planId - the ID of the new plan to upgrade to
 */
public record UpgradeSubscriptionResource(Long accountId, Long planId) {}
