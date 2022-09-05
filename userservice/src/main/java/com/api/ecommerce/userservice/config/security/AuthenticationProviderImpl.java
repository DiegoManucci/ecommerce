package com.api.ecommerce.userservice.config.security;

import com.api.ecommerce.userservice.exceptions.EmailNotFoundException;
import com.api.ecommerce.userservice.exceptions.InvalidPasswordException;
import com.api.ecommerce.userservice.models.UserModel;
import com.api.ecommerce.userservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

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
