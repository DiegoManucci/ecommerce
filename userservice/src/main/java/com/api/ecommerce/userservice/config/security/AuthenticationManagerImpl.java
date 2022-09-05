package com.api.ecommerce.userservice.config.security;

import com.api.ecommerce.userservice.exceptions.EmailNotFoundException;
import com.api.ecommerce.userservice.exceptions.InvalidPasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationManagerImpl implements AuthenticationManager {

    @Autowired
    private AuthenticationProviderImpl authenticationProvider;

    @Override
    public Authentication authenticate(Authentication authentication) throws EmailNotFoundException, InvalidPasswordException {
        return authenticationProvider.authenticate(authentication);
    }
}
