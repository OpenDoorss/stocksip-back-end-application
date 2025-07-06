package com.stocksip.authentication.domain.services;

import com.stocksip.authentication.domain.model.aggregates.User;
import com.stocksip.authentication.domain.model.queries.GetUserByIdQuery;
import com.stocksip.authentication.domain.model.queries.GetUserByUsernameQuery;

import java.util.Optional;

/**
 * User query service
 * <p>
 *     This interface represents the service to handle user queries.
 * </p>
 */
public interface UserQueryService {

    /**
     * Handle get user by id query
     * @param query the {@link GetUserByIdQuery} query
     * @return an {@link Optional} of {@link User} entity
     */
    Optional<User> handle(GetUserByIdQuery query);

    /**
     * Handle get user by username query
     * @param query the {@link GetUserByUsernameQuery} query
     * @return an {@link Optional} of {@link User} entity
     */
    Optional<User> handle(GetUserByUsernameQuery query);
}
