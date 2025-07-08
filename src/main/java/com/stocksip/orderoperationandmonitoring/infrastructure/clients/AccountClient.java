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

    /**
     * Devuelve la cuenta que coincide con el e‑mail o Optional.empty() si no existe
     * o si el endpoint responde 404.
     */
    public Optional<AccountDTO> getAccountByEmail(String email) {
        // 1️⃣  Ruta del nuevo endpoint (sin token porque es permitAll)
        String encoded = UriUtils.encode(email, StandardCharsets.UTF_8);
        String url = "http://localhost:8080/api/v1/accounts/by-email?email=" + encoded;

        try {
            ResponseEntity<AccountDTO> response =
                    restTemplate.getForEntity(url, AccountDTO.class);

            return Optional.ofNullable(response.getBody());

        } catch (HttpClientErrorException.NotFound ex) {
            // 404 → no existe proveedor con ese correo
            return Optional.empty();

        } catch (Exception ex) {
            // 400, 500 u otro error → registra o maneja si lo necesitas
            return Optional.empty();
        }
    }
}
