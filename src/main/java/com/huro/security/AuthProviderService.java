package com.huro.security;

import com.huro.model.entity.HuroUser;
import com.huro.security.factory.UsernamePasswordAuthenticationTokenFactory;
import com.huro.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
public class AuthProviderService implements AuthenticationProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthProviderService.class);

    private UserService userService;
    private UsernamePasswordAuthenticationTokenFactory usernamePasswordAuthenticationTokenFactory;

    public AuthProviderService(UserService userService, UsernamePasswordAuthenticationTokenFactory usernamePasswordAuthenticationTokenFactory) {
        this.userService = userService;
        this.usernamePasswordAuthenticationTokenFactory = usernamePasswordAuthenticationTokenFactory;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        String password = authentication.getCredentials().toString();
        LOGGER.info("Doing login " + login);
        HuroUser u = userService.isLoginValid(login, password);
        if(u != null) {
            LOGGER.info("Login successful. User: " + login);
            return usernamePasswordAuthenticationTokenFactory.create(u);
        }
        throw new UsernameNotFoundException("Not valid login/password");
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
