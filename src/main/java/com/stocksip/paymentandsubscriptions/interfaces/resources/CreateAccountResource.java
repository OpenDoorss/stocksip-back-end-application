package com.stocksip.paymentandsubscriptions.interfaces.resources;

public record CreateAccountResource(String email,
                                    String role,
                                    String plan) {
}
