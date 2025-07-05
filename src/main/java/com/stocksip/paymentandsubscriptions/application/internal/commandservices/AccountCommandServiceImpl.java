package com.stocksip.paymentandsubscriptions.application.internal.commandservices;

import com.stocksip.paymentandsubscriptions.application.internal.outboundservices.acl.ExternalAuthenticationService;
import com.stocksip.paymentandsubscriptions.domain.model.aggregates.Account;
import com.stocksip.paymentandsubscriptions.domain.model.commands.CreateAccountCommand;
import com.stocksip.paymentandsubscriptions.infrastructure.persistence.jpa.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountCommandServiceImpl implements com.stocksip.paymentandsubscriptions.domain.services.AccountCommandService {

    private final AccountRepository accountRepository;
    private final ExternalAuthenticationService externalAuthenticationService;

    public AccountCommandServiceImpl(AccountRepository accountRepository, ExternalAuthenticationService externalAuthenticationService) {
        this.accountRepository = accountRepository;
        this.externalAuthenticationService = externalAuthenticationService;
    }

    @Override
    public Optional<Account> handle(CreateAccountCommand command) {

        if (!command.password().equals(command.validatePassword())) {
            throw new IllegalArgumentException("Passwords do not match.");
        }

        Long userId = externalAuthenticationService.createUser(command.username(), command.password());

        var account = new Account(userId, command.accountRole(), command.businessName());
        var createdAccount = accountRepository.save(account);
        return Optional.of(createdAccount);
    }
}
