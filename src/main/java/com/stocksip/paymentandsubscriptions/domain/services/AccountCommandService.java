package com.stocksip.paymentandsubscriptions.domain.services;

import com.stocksip.paymentandsubscriptions.domain.model.aggregates.Account;
import com.stocksip.paymentandsubscriptions.domain.model.commands.CreateAccountCommand;

import java.util.Optional;

/**
 * This interface defines the contract for handling account-related commands.
 * It provides methods to create and manage accounts within the payment and subscription domain.
 *
 * @since 1.0.0
 */
public interface AccountCommandService {

    /**
     * Handles the creation of a new account based on the provided command.
     * @param command The command containing the necessary information to create an account.
     */
    Optional<Account> handle(CreateAccountCommand command);

    Optional<Account> getById(Long accountId);

}
