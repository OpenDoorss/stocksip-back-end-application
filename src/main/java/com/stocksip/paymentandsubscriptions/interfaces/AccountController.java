package com.stocksip.paymentandsubscriptions.interfaces;

import com.stocksip.paymentandsubscriptions.domain.model.aggregates.Account;
import com.stocksip.paymentandsubscriptions.domain.services.AccountCommandService;
import com.stocksip.paymentandsubscriptions.interfaces.resources.AccountResource;
import com.stocksip.paymentandsubscriptions.interfaces.resources.CreateAccountResource;
import com.stocksip.paymentandsubscriptions.interfaces.transform.AccountResourceFromEntityAssembler;
import com.stocksip.paymentandsubscriptions.interfaces.transform.CreateAccountCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;

/**
 * REST controller for managing accounts.
 *
 * @summary
 * This class provides REST endpoints for account management, including creating and managing accounts.
 *
 * @since 1.0.0
 */
@RestController
@RequestMapping(value = "api/v1/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Accounts", description = "Endpoints for managing accounts.")
public class AccountController {

    private final AccountCommandService accountCommandService;

    public AccountController(AccountCommandService accountCommandService) {
        this.accountCommandService = accountCommandService;
    }

    @Operation(
            summary = "Create a new account",
            description = "This endpoint allows you to create a new account with the provided details."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Account created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PostMapping
    public ResponseEntity<AccountResource> createAccount(@RequestBody CreateAccountResource resource)
    {
        Optional<Account> account = accountCommandService.handle(CreateAccountCommandFromResourceAssembler.toCommandFromResource(resource));

        return account.map(source ->
                        new ResponseEntity<>(AccountResourceFromEntityAssembler.toResourceFromEntity(source), CREATED)).orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
