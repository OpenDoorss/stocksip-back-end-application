package com.stocksip.paymentandsubscriptions.domain.model.aggregates;

import com.stocksip.paymentandsubscriptions.domain.model.commands.CreateAccountCommand;
import com.stocksip.paymentandsubscriptions.domain.model.valueobjects.Role;
import com.stocksip.paymentandsubscriptions.domain.model.valueobjects.UserId;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;


/**
 * This is an aggregate root that represents an account in the payment and subscription domain.
 * The class encapsulates the account's email, role, and plan.
 *
 * @summary
 * The Account class is an aggregate root that encapsulates the email, role, and plan of a user account in the payment and subscription domain.
 *
 * @since 1.0.0
 */
@Entity
@Getter
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long accountId;

    @Column(unique = true, nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "userId", column = @Column(nullable = false))
    })
    private UserId userId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "role", column = @Column(nullable = false))
    })
    private Role role;

    // Default constructor for JPA
    public Account() {}

    public Account(CreateAccountCommand command) {
        this.createdAt = LocalDateTime.now();
        this.role = new Role(command.role());
    }

}
