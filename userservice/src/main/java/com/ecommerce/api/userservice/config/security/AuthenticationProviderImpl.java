package com.ecommerce.api.userservice.config.security;

import com.ecommerce.api.userservice.exceptions.EmailNotFoundException;
import com.ecommerce.api.userservice.exceptions.InvalidPasswordException;
import com.ecommerce.api.userservice.models.UserModel;
import com.ecommerce.api.userservice.repositories.UserRepository;
import com.ecommerce.api.userservice.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.net.PasswordAuthentication;

@Component
public class AuthenticationProviderImpl implements AuthenticationProvider {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws EmailNotFoundException, InvalidPasswordException {

        UserModel userModel = userRepository.findByEmail(authentication.getPrincipal().toString())
                .orElseThrow(() -> new EmailNotFoundException(authentication.getPrincipal().toString()));

        if(!passwordEncoder.matches(authentication.getCredentials().toString(), userModel.getPassword())){
            throw new InvalidPasswordException();
        }

        return new EmailPasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(), Boolean.TRUE);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
