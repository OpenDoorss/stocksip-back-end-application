package com.stocksip.paymentandsubscriptions.infrastructure.paymentproviders.paypal.client;

import com.stocksip.paymentandsubscriptions.infrastructure.paymentproviders.paypal.configuration.PayPalConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Component
public class PayPalClient {

    private final PayPalConfiguration properties;
    private final RestTemplate restTemplate;

    public PayPalClient(PayPalConfiguration properties) {
        this.properties = properties;
        this.restTemplate = new RestTemplate();
    }

    public String getAccessToken() {
        String url = properties.getMode() + "/v1/oauth2/token";

        String credentials = properties.getClientId() + ":" + properties.getClientKey();
        String encoded = Base64.getEncoder().encodeToString(credentials.getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(encoded);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> request = new HttpEntity<>("grant_type=client_credentials", headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, request, Map.class);

        return (String) response.getBody().get("access_token");
    }
}
