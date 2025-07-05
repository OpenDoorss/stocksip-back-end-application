package com.stocksip.paymentandsubscriptions.application.acl;

import com.stocksip.paymentandsubscriptions.domain.model.aggregates.Account;
import com.stocksip.paymentandsubscriptions.infrastructure.persistence.jpa.AccountRepository;
import com.stocksip.paymentandsubscriptions.interfaces.acl.PaymentAndSubscriptionFacade;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentAndSubscriptionFacadeImpl implements PaymentAndSubscriptionFacade {

    private final AccountRepository accountRepository;

    public PaymentAndSubscriptionFacadeImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Long GetAccountIdByUserId(Long userId) {
        Optional<Account> accountOpt = accountRepository.findById(userId);
        return accountOpt.map(Account::getAccountId).orElse(0L);
    }
}
