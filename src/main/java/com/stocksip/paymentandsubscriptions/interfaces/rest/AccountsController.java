package com.stocksip.paymentandsubscriptions.interfaces.rest;

import com.stocksip.paymentandsubscriptions.domain.model.aggregates.Account;
import com.stocksip.paymentandsubscriptions.domain.model.queries.GetAccountStatusByIdQuery;
import com.stocksip.paymentandsubscriptions.domain.services.AccountCommandService;
import com.stocksip.paymentandsubscriptions.domain.services.AccountQueryService;
import com.stocksip.paymentandsubscriptions.infrastructure.persistence.jpa.AccountRepository;
import com.stocksip.paymentandsubscriptions.interfaces.rest.resources.AccountResource;
import com.stocksip.paymentandsubscriptions.interfaces.rest.resources.AccountStatusResource;
import com.stocksip.paymentandsubscriptions.interfaces.rest.resources.CreateAccountResource;
import com.stocksip.paymentandsubscriptions.interfaces.rest.transform.AccountResourceFromEntityAssembler;
import com.stocksip.paymentandsubscriptions.interfaces.rest.transform.CreateAccountCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;

/**
 * AccountsController
 * <p>
 *     This controller is responsible for handling account-related requests.
 *     It exposes the following endpoint:
 *     <ul>
 *         <li>POST /api/v1/accounts/sign-up</li>
 *     </ul>
 * </p>
 */
@RestController
@RequestMapping(value = "api/v1/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Accounts", description = "Available Accounts Endpoints.")
public class AccountsController {

    private final AccountCommandService accountCommandService;
    private final AccountQueryService accountQueryService;
    private final AccountRepository accountRepository;

    public AccountsController(AccountCommandService accountCommandService, AccountQueryService accountQueryService, AccountRepository accountRepository)  {
        this.accountCommandService = accountCommandService;
        this.accountQueryService = accountQueryService;
        this.accountRepository = accountRepository;
    }

    /* ────────────── CREATE ────────────── */
    @Operation(summary = "Sign-up",
            description = "Sign-up with the provided credentials.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Account created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PostMapping("sign-up")
    public ResponseEntity<AccountResource> createAccount(@RequestBody CreateAccountResource body) {

        Optional<Account> account = accountCommandService.handle(
                CreateAccountCommandFromResourceAssembler.toCommandFromResource(body));

        return account
                .map(a -> new ResponseEntity<>(AccountResourceFromEntityAssembler.toResourceFromEntity(a), CREATED))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    /* ────────────── READ ────────────── */
    @Operation(summary = "Get account by ID",
            description = "Returns the account (username, role, status, business name, etc.) with the given ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Account found"),
            @ApiResponse(responseCode = "404", description = "Account not found")
    })
    @GetMapping("{accountId}")
    public ResponseEntity<AccountResource> getAccountById(@PathVariable Long accountId) {

        Optional<Account> accountOpt = accountCommandService.getById(accountId);

        return accountOpt
                .map(AccountResourceFromEntityAssembler::toResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/by-email")
    public ResponseEntity<AccountResource> getByEmail(@RequestParam String email) {

        return accountRepository.findByEmailAndRole(email, "Supplier")
                .map(a -> ResponseEntity.ok(
                        AccountResourceFromEntityAssembler.toResourceFromEntity(a)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("{accountId}/status")
    public ResponseEntity<AccountStatusResource> getAccountStatus(@PathVariable Long accountId) {
        Optional<String> statusOpt = accountQueryService.handle(new GetAccountStatusByIdQuery(accountId));
        if (statusOpt.isEmpty()) {return ResponseEntity.notFound().build();}
        return ResponseEntity.ok(new AccountStatusResource(statusOpt.get()));
    }
}
