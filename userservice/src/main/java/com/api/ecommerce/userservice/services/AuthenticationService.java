package com.api.ecommerce.userservice.services;

import com.api.ecommerce.userservice.exceptions.EmailNotFoundException;
import com.api.ecommerce.userservice.models.UserModel;
import com.api.ecommerce.userservice.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
    public UserDetails loadUserByUsername(String email) throws EmailNotFoundException { // loadUserByEmail // Spring forces you to use this method // using email instead of username
        UserModel userModel = userRepository.findByEmail(email)
                .orElseThrow(() -> new EmailNotFoundException(email));

        return new UserModel(userModel.getEmail(), userModel.getUsername(), userModel.getPassword(), userModel.getRoles(), true, true, true, true);
    }

    @Transactional
    public UserModel loadUserByEmail(String email) throws EmailNotFoundException {
        UserModel userModel = userRepository.findByEmail(email)
                .orElseThrow(() -> new EmailNotFoundException(email));

        return new UserModel(userModel.getEmail(), userModel.getUsername(), userModel.getPassword(), userModel.getRoles(), true, true, true, true);
    }

}