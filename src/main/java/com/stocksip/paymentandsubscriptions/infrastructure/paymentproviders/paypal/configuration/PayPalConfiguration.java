package com.stocksip.paymentandsubscriptions.infrastructure.paymentproviders.paypal.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
public class PayPalConfiguration {

    @Value("${paypal.client-id}")
    private String clientId;

    @Value("${paypal.client-key}")
    private String clientKey;

    @Value("${paypal.mode}")
    private String mode;
}
