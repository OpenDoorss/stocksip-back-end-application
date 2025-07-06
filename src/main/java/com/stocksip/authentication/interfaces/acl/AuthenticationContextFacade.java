package com.stocksip.authentication.interfaces.acl;

import java.util.Optional;

public interface AuthenticationContextFacade {

    Long createUser(String username, String password);
}
