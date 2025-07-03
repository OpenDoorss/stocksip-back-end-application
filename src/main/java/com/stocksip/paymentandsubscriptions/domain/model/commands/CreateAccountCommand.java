package com.stocksip.paymentandsubscriptions.domain.model.commands;

/**
 *
 *
 * @summary
 * The CreateAccountCommand class is a record that encapsulates the information needed to create a new account.
 *
 * @since 1.0.0
 */
public record CreateAccountCommand(String email,
                                   Long userId,
                                   String role,

                                    String plan,

                                   String businessName) {
}
