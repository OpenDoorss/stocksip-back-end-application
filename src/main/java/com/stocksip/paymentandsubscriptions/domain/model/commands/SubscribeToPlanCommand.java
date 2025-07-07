package com.stocksip.paymentandsubscriptions.domain.model.commands;

/**
 * This command is used to subscribe an account to a specific plan.
 * @param accountId - the ID of the account to subscribe
 * @param planId - the ID of the plan to subscribe to
 */
public record SubscribeToPlanCommand(Long accountId, Long planId) {}
