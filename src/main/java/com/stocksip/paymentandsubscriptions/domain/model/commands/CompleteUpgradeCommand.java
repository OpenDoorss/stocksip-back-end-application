package com.stocksip.paymentandsubscriptions.domain.model.commands;

/**
 * This command is used to complete the upgrade of a subscription plan for a user.
 * @param token - the token to verify the upgrade
 * @param accountId - the ID of the account to upgrade
 * @param planId - the ID of the new plan to upgrade to
 */
public record CompleteUpgradeCommand(String token, Long accountId, Long planId) {}
