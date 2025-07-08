package com.stocksip.paymentandsubscriptions.application.internal.outboundservices.payments;

/**
 * This interface defines the contract for payment services.
 */
public interface PaymentService {

    /**
     * This method creates an order for a subscription plan.
     * @param planName - the name of the subscription plan
     * @param amount - the amount to be charged for the subscription
     * @param returnUrl - the URL to redirect to after payment is successful
     * @param cancelUrl - the URL to redirect to if the payment is cancelled
     * @return A string representing the order ID or payment link.
     */
    String createOrder(String planName, double amount, String returnUrl, String cancelUrl);

    /**
     * This method captures the payment for an order.
     * @param orderId - the orderId received after creating the order
     */
    void captureOrder(String orderId);
}
