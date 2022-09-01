package com.ecommerce.api.userservice.config.security;

import com.ecommerce.api.userservice.exceptions.EmailNotFoundException;
import com.ecommerce.api.userservice.exceptions.InvalidPasswordException;
import com.ecommerce.api.userservice.models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
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
