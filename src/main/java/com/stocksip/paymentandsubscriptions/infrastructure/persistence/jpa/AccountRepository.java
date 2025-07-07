package com.stocksip.paymentandsubscriptions.infrastructure.persistence.jpa;

import com.stocksip.paymentandsubscriptions.domain.model.aggregates.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * This interface extends JpaRepository to provide CRUD operations for the Account entity.
 * It allows for easy interaction with the database without needing to implement common data access methods.
 *
 * @since 1.0.0
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("SELECT a.status FROM Account a WHERE a.accountId = :accountId")
    Optional<String> findStatusByAccountId(@Param("accountId") Long accountId);
}
