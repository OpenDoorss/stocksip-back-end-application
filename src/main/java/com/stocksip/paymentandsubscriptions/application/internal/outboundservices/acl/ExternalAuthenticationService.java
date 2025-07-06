package com.stocksip.paymentandsubscriptions.application.internal.outboundservices.acl;

import com.stocksip.authentication.interfaces.acl.AuthenticationContextFacade;
import org.springframework.stereotype.Service;

@Service
public class ExternalAuthenticationService {

    private final AuthenticationContextFacade authenticationContextFacade;

    public ExternalAuthenticationService(AuthenticationContextFacade authenticationContextFacade) {
        this.authenticationContextFacade = authenticationContextFacade;
    }

    public Long createUser(String username, String password) {
        return authenticationContextFacade.createUser(username, password);
    }
}
