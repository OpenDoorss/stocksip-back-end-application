package com.stocksip.paymentandsubscriptions.infrastructure.persistence.jpa;

import com.stocksip.paymentandsubscriptions.domain.model.aggregates.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This interface extends JpaRepository to provide CRUD operations for the Account entity.
 * It allows for easy interaction with the database without needing to implement common data access methods.
 *
 * @since 1.0.0
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    boolean existsByGeneralEmail(GeneralEmail generalEmail);
}
