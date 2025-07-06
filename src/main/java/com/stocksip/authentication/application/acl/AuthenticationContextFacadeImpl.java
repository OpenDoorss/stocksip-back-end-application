package com.stocksip.authentication.application.acl;

import com.stocksip.authentication.domain.model.commands.SignInCommand;
import com.stocksip.authentication.domain.model.commands.SignUpCommand;
import com.stocksip.authentication.domain.services.UserCommandService;
import com.stocksip.authentication.interfaces.acl.AuthenticationContextFacade;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationContextFacadeImpl implements AuthenticationContextFacade {

    private final UserCommandService userCommandService;

    public AuthenticationContextFacadeImpl(UserCommandService userCommandService) {
        this.userCommandService = userCommandService;
    }

    @Override
    public Long createUser(String username, String password) {

        var signupCommand = new SignUpCommand(username, password);
        var user = userCommandService.handle(signupCommand);
        return user.isEmpty() ? Long.valueOf(0L) : user.get().getUserId();
    }
}
