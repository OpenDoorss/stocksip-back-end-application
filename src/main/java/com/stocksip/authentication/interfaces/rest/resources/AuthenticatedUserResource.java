package com.stocksip.authentication.interfaces.rest.resources;

public record AuthenticatedUserResource(Long userId, String username, String token, Long accountId) {}
