package com.stocksip.orderoperationandmonitoring.infrastructure.clients;

import com.stocksip.orderoperationandmonitoring.infrastructure.dto.AccountDTO;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Component
public class AccountClient {

    private final RestTemplate restTemplate;

    public AccountClient(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public Optional<AccountDTO> getAccountByEmail(String email) {
        String url = "http://localhost:8080/api/v1/accounts?email=" + email;
        try {
            ResponseEntity<AccountDTO> response =
                    restTemplate.getForEntity(url, AccountDTO.class); // ya no array

            return Optional.ofNullable(response.getBody());

        } catch (Exception e) {
            return Optional.empty();
        }
    }

}
