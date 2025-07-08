package com.stocksip.paymentandsubscriptions.application.internal.queryservices;

import com.stocksip.paymentandsubscriptions.domain.model.aggregates.Account;
import com.stocksip.paymentandsubscriptions.domain.model.queries.GetAccountByEmailQuery;
import com.stocksip.paymentandsubscriptions.domain.model.queries.GetAccountStatusByIdQuery;
import com.stocksip.paymentandsubscriptions.domain.services.AccountQueryService;
import com.stocksip.paymentandsubscriptions.infrastructure.persistence.jpa.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class AccountQueryServiceImpl implements AccountQueryService {

    private final AccountRepository accountRepository;

    public AccountQueryServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Optional<Account> handle(GetAccountByEmailQuery query) {
        return Optional.empty();
    }

    @Override
    public Optional<String> handle(GetAccountStatusByIdQuery query) {
        return accountRepository.findStatusByAccountId(query.accountId());
    }
}
