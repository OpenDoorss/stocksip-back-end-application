package com.stocksip.paymentandsubscriptions.domain.model.aggregates;

import com.stocksip.paymentandsubscriptions.domain.model.commands.CreateAccountCommand;
import com.stocksip.paymentandsubscriptions.domain.model.valueobjects.*;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Embedded
    @AttributeOverride(
            name = "value",
            column = @Column(name = "userOwnerId", nullable = false)
    )
    private UserOwnerId userOwnerId;

    @Enumerated(EnumType.STRING)
    private AccountRole accountRole;

    @Embedded
    @AttributeOverride(
            name = "value",
            column = @Column(name = "businessName", nullable = false)
    )
    private BusinessName businessName;

    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    protected Account() {}

    public Account(Long userOwnerId, String accountRole, String businessName) {
        this.userOwnerId  = new UserOwnerId(userOwnerId);
        this.accountRole  = AccountRole.valueOf(accountRole);
        this.status       = AccountStatus.INACTIVE;
        this.businessName = new BusinessName(businessName);
    }

    public Account(CreateAccountCommand command) {
        this.createdAt    = LocalDateTime.now();
        this.accountRole  = AccountRole.valueOf(command.accountRole());
        this.status       = AccountStatus.INACTIVE;
        this.businessName = new BusinessName(command.businessName());
    }
}
