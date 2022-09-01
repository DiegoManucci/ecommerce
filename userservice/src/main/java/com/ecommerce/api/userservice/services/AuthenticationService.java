package com.ecommerce.api.userservice.services;

import com.ecommerce.api.userservice.models.UserModel;
import com.ecommerce.api.userservice.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class AuthenticationService implements UserDetailsService { // Spring Security WebSecurityConfig class already know to use this service by default

    private final UserRepository userRepository;

    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { // loadUserByEmail
        String email = username;
        UserModel userModel = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found With Email: " + email)); // create EmailNotFoundException ?

        return new UserModel(userModel.getEmail(), userModel.getUsername(), userModel.getPassword(), userModel.getRoles(), true, true, true, true);
    }

    @Transactional
    public UserModel loadUserByEmail(String email) throws UsernameNotFoundException { // loadUserByEmail
        UserModel userModel = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found With Email: " + email)); // create EmailNotFoundException ?

        return new UserModel(userModel.getEmail(), userModel.getUsername(), userModel.getPassword(), userModel.getRoles(), true, true, true, true);
    }
}
