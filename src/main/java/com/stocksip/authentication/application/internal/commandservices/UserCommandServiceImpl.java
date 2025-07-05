package com.stocksip.authentication.application.internal.commandservices;

import com.stocksip.authentication.application.internal.outboundservices.hashing.HashingService;
import com.stocksip.authentication.application.internal.outboundservices.tokens.TokenService;
import com.stocksip.authentication.domain.model.aggregates.User;
import com.stocksip.authentication.domain.model.commands.SignInCommand;
import com.stocksip.authentication.domain.model.commands.SignUpCommand;
import com.stocksip.authentication.domain.services.UserCommandService;
import com.stocksip.authentication.infrastructure.persistence.jpa.repositories.UserRepository;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;
    private final HashingService hashingService;
    private final TokenService tokenService;

    public UserCommandServiceImpl(UserRepository userRepository, HashingService hashingService, TokenService tokenService) {
        this.userRepository = userRepository;
        this.hashingService = hashingService;
        this.tokenService = tokenService;
    }

    @Override
    public Optional<ImmutablePair<User, String>> handle(SignInCommand command) {
        var user = userRepository.findByUsername(command.username());
        if (user.isEmpty())
            throw new RuntimeException("User not found");
        if (!hashingService.matches(command.password(), user.get().getPassword()))
            throw new RuntimeException("Invalid password");
        var token = tokenService.generateToken(user.get().getUsername());
        return Optional.of(ImmutablePair.of(user.get(), token));
    }

    @Override
    public Optional<User> handle(SignUpCommand command) {
        if (userRepository.existsByUsername(command.username()))
            throw new RuntimeException("Username already exists");
        var user = new User(command.username(), hashingService.encode(command.password()));
        userRepository.save(user);
        return userRepository.findByUsername(command.username());
    }
}
