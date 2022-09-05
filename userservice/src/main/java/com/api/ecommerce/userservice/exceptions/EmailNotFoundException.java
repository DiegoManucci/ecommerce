package com.api.ecommerce.userservice.exceptions;

import org.springframework.security.core.AuthenticationException;

public class EmailNotFoundException extends AuthenticationException {
    public EmailNotFoundException(String email) {
        super("User Not Found With Email: " + email);
    }
}
