package com.stocksip.paymentandsubscriptions.domain.services;

import com.stocksip.paymentandsubscriptions.domain.model.commands.CompleteSubscriptionCommand;
import com.stocksip.paymentandsubscriptions.domain.model.commands.SubscribeToPlanCommand;

import java.util.Optional;

/**
 * This service handles commands related to subscription management.
 */
public interface SubscriptionCommandService {

    /**
     * Handles the command to subscribe to a plan.
     * @param command - the command containing subscription details
     * @return an Optional containing the subscription ID if successful, or empty if not
     */
    Optional<String> handle(SubscribeToPlanCommand command);

    /**
     * Handles the command to complete a subscription.
     * @param command - the command containing details to complete the subscription
     */
    void handle(CompleteSubscriptionCommand command);

}
