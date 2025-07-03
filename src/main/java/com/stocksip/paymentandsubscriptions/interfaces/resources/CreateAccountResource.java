package com.stocksip.paymentandsubscriptions.interfaces.resources;

public record CreateAccountResource(String email,

                                    Long userOwnerId,
                                    String role,
                                    String plan,
                                    String businessName) {
}
