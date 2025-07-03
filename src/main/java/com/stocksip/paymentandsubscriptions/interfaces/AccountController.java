package com.stocksip.paymentandsubscriptions.interfaces;

import com.stocksip.paymentandsubscriptions.domain.model.aggregates.Account;
import com.stocksip.paymentandsubscriptions.domain.model.queries.GetAccountByEmailQuery;
import com.stocksip.paymentandsubscriptions.domain.services.AccountCommandService;
import com.stocksip.paymentandsubscriptions.domain.services.AccountQueryService;
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
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping(value = "api/v1/accounts",   // ← base path UNA sola vez
        produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Accounts", description = "Endpoints for managing accounts.")
public class AccountController {

    private final AccountCommandService accountCommandService;
    private final AccountQueryService   accountQueryService;

    public AccountController(AccountCommandService accountCommandService,
                             AccountQueryService accountQueryService) {
        this.accountCommandService = accountCommandService;
        this.accountQueryService   = accountQueryService;
    }

    /* ────────────── CREATE ────────────── */
    @Operation(summary = "Create a new account",
            description = "Creates a new account with the provided details.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Account created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PostMapping
    public ResponseEntity<AccountResource> createAccount(@RequestBody CreateAccountResource body) {

        Optional<Account> account = accountCommandService.handle(
                CreateAccountCommandFromResourceAssembler.toCommandFromResource(body));

        return account
                .map(a -> new ResponseEntity<>(AccountResourceFromEntityAssembler.toResourceFromEntity(a), CREATED))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    /* ────────────── READ BY EMAIL ────────────── */
    @Operation(summary = "Get account by email",
            description = "Returns the account that matches the given email.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Account found"),
            @ApiResponse(responseCode = "404", description = "Account not found")
    })
    @GetMapping
    public ResponseEntity<AccountResource> getByEmail(@RequestParam String email) {
        return accountQueryService.handle(new GetAccountByEmailQuery(email))
                .map(AccountResourceFromEntityAssembler::toResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
