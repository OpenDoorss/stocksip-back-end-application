package com.stocksip.authentication.application.internal.outboundservices.acl;

import com.stocksip.paymentandsubscriptions.interfaces.acl.PaymentAndSubscriptionFacade;
import org.springframework.stereotype.Service;

@Service
public class ExternalPaymentAndSubscriptionService {

    private final PaymentAndSubscriptionFacade  paymentAndSubscriptionFacade;

    public ExternalPaymentAndSubscriptionService(PaymentAndSubscriptionFacade paymentAndSubscriptionFacade) {
        this.paymentAndSubscriptionFacade = paymentAndSubscriptionFacade;
    }

    public Long getAccountIdByUserId(Long userId) {
        return paymentAndSubscriptionFacade.GetAccountIdByUserId(userId);
    }
}
