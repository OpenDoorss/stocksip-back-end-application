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

    @Embedded
    @AttributeOverride(
            name = "value",
            column = @Column(name = "role", nullable = false)
    )
    private Role role;

    @Embedded
    @AttributeOverride(
            name = "value",
            column = @Column(name = "email", nullable = false, unique = true)
    )
    private GeneralEmail email;


    @Embedded
    @AttributeOverride(
            name = "value",
            column = @Column(name = "businessName", nullable = false)
    )
    private BusinessName businessName;


    protected Account() {}

    public Account(CreateAccountCommand cmd) {
        this.createdAt    = LocalDateTime.now();
        this.userOwnerId = new UserOwnerId(cmd.userOwnerId());
        this.role         = new Role(cmd.role());
        this.email        = new GeneralEmail(cmd.email());
        this.businessName = new BusinessName(cmd.businessName());
    }
}
