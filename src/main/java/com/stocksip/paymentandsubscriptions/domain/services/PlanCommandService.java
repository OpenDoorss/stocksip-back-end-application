package com.stocksip.paymentandsubscriptions.domain.services;

import com.stocksip.paymentandsubscriptions.domain.model.commands.SeedPlansCommand;

/**
 * This interface defines the contract for handling commands related to plans.
 */
public interface PlanCommandService {

    /*
    * Handles the command to seed plans with the provided details.
    */
    void handle(SeedPlansCommand command);
}
