package com.stocksip.paymentandsubscriptions.interfaces.rest.resources;

/**
 * This resource is used to complete the upgrade process for a user.
 * @param token - the token to verify the upgrade
 * @param accountId - the ID of the account being upgraded
 * @param planId - the ID of the plan to which the account is being upgraded
 */
public record CompleteUpgradeResource(String token, Long accountId, Long planId) {}
