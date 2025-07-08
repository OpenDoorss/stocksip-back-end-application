package com.stocksip.paymentandsubscriptions.domain.model.commands;

/**
 * This command is used to upgrade a subscription plan for a user.
 * @param accountId - the ID of the account to upgrade
 * @param planId - the ID of the new plan to upgrade to
 */
public record UpgradeSubscriptionCommand(Long accountId, Long planId) {}
