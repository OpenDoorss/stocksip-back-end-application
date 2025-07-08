package com.stocksip.paymentandsubscriptions.infrastructure.paymentproviders.paypal.services;

import com.stocksip.paymentandsubscriptions.application.internal.outboundservices.payments.PaymentService;
import com.stocksip.paymentandsubscriptions.infrastructure.paymentproviders.paypal.client.PayPalClient;
import com.stocksip.paymentandsubscriptions.infrastructure.paymentproviders.paypal.configuration.PayPalConfiguration;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.List;
import java.util.Map;

@Service
public class PayPalServiceImpl implements PaymentService {

    private final PayPalClient client;
    private final PayPalConfiguration properties;
    private final RestTemplate restTemplate;

    public PayPalServiceImpl(PayPalClient client, PayPalConfiguration properties) {
        this.client = client;
        this.properties = properties;
        this.restTemplate = new RestTemplate();
    }

    @Override
    public String createOrder(String planName, double amount, String returnUrl, String cancelUrl) {
        String token = client.getAccessToken();

        Map<String, Object> body = Map.of(
                "intent", "CAPTURE",
                "purchase_units", List.of(
                        Map.of(
                                "description", "Subscription: " + planName,
                                "amount", Map.of(
                                        "currency_code", "USD",
                                        "value", String.format("%.2f", amount),
                                        "breakdown", Map.of(
                                                "item_total", Map.of("currency_code", "USD", "value", String.format("%.2f", amount))
                                        )
                                ),
                                "items", List.of(
                                        Map.of(
                                                "name", planName,
                                                "sku", "premium_sub",
                                                "unit_amount", Map.of("currency_code", "USD", "value", String.format("%.2f", amount)),
                                                "quantity", "1",
                                                "category", "DIGITAL_GOODS"
                                        )
                                )
                        )
                ),
                "application_context", Map.of(
                        "brand_name", "StockSip",
                        "locale", "en-US",
                        "user_action", "PAY_NOW",
                        "return_url", returnUrl,
                        "cancel_url", cancelUrl
                )
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                properties.getMode() + "/v2/checkout/orders",
                HttpMethod.POST,
                request,
                Map.class
        );

        List<Map<String, String>> links = (List<Map<String, String>>) response.getBody().get("links");

        return links.stream()
                .filter(link -> "approve".equals(link.get("rel")))
                .findFirst()
                .map(link -> link.get("href"))
                .orElseThrow(() -> new RuntimeException("Approval URL not found"));
    }

    @Override
    public void captureOrder(String orderId) {
        String token = client.getAccessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>("{}", headers);

        ResponseEntity<String> response = restTemplate.exchange(
                properties.getMode() + "/v2/checkout/orders/" + orderId + "/capture",
                HttpMethod.POST,
                request,
                String.class
        );

        System.out.println("Capture response: " + response.getBody());
    }
}
