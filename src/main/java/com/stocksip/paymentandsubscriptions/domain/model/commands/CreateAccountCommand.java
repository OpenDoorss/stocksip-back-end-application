package com.stocksip.paymentandsubscriptions.domain.model.commands;

/**
 * This command is used to create a new account in the payment and subscription domain.
 * It encapsulates the necessary information required to create an account, including
 * the email, role, and plan.
 *
 * @summary
 * The CreateAccountCommand class is a record that encapsulates the information needed to create a new account.
 *
 * @since 1.0.0
 */
public record CreateAccountCommand(String email,
                                   String role,
                                   String plan) {
}
