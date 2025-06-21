package com.stocksip.paymentandsubscriptions.domain.model.aggregates;

import com.stocksip.paymentandsubscriptions.domain.model.commands.CreateAccountCommand;
import com.stocksip.paymentandsubscriptions.domain.model.valueobjects.GeneralEmail;
import com.stocksip.paymentandsubscriptions.domain.model.valueobjects.Plan;
import com.stocksip.paymentandsubscriptions.domain.model.valueobjects.Role;
import jakarta.persistence.*;
import lombok.Getter;


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
    public long accountId;

    @Column(unique = true, nullable = false)
    @Embedded()
    private GeneralEmail email;

    @Column(unique = true, nullable = false)
    @Embedded()
    public Role role;

    @Column(unique = true, nullable = false)
    @Embedded()
    public Plan plan;

    // Default constructor for JPA
    public Account() {}

    public Account(CreateAccountCommand command) {
        this.email = new GeneralEmail(command.email());
        this.role = new Role(command.role());
        this.plan = new Plan(command.plan());
    }

}
