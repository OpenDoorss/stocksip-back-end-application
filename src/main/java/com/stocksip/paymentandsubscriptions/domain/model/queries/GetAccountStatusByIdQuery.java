package com.stocksip.paymentandsubscriptions.domain.model.queries;

/**
 * This record represents a query to get the status of an account by its ID.
 * @param accountId - the ID of the account for which the status is being queried.
 */
public record GetAccountStatusByIdQuery(Long accountId) {}
