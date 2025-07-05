package com.stocksip.authentication.application.internal.queryservices;

import com.stocksip.authentication.domain.model.aggregates.User;
import com.stocksip.authentication.domain.model.queries.GetUserByIdQuery;
import com.stocksip.authentication.domain.model.queries.GetUserByUsernameQuery;
import com.stocksip.authentication.domain.services.UserQueryService;
import com.stocksip.authentication.infrastructure.persistence.jpa.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserQueryServiceImpl implements UserQueryService {

    private final UserRepository userRepository;

    public UserQueryServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> handle(GetUserByIdQuery query) {
        return userRepository.findById(query.userId());
    }

    @Override
    public Optional<User> handle(GetUserByUsernameQuery query) {
        return userRepository.findByUsername(query.username());
    }
}
