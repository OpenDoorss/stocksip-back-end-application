package com.stocksip.paymentandsubscriptions.interfaces.rest.resources;

public record CreateAccountResource(String username,
                                    String password,
                                    String validatePassword,
                                    String accountRole,
                                    String businessName) {
}
