package com.stocksip.paymentandsubscriptions.application.internal.commandservices;

import com.stocksip.paymentandsubscriptions.domain.model.aggregates.Account;
import com.stocksip.paymentandsubscriptions.domain.model.commands.CreateAccountCommand;
import com.stocksip.paymentandsubscriptions.domain.model.valueobjects.GeneralEmail;
import com.stocksip.paymentandsubscriptions.domain.services.AccountCommandService;
import com.stocksip.paymentandsubscriptions.infrastructure.persistence.jpa.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountCommandServiceImpl implements AccountCommandService {

    private final AccountRepository accountRepository;

    public AccountCommandServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Optional<Account> handle(CreateAccountCommand command) {

        if (accountRepository.existsByEmail(GeneralEmail.from(command.email()))) {
            throw new IllegalArgumentException("This email is already used.");
        }

        var account = new Account(command);
        var createdAccount = accountRepository.save(account);
        return Optional.of(createdAccount);
    }
}
