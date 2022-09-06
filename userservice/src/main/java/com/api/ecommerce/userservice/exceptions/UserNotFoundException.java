package com.api.ecommerce.userservice.exceptions;

import org.springframework.security.core.AuthenticationException;

public class UserNotFoundException extends AuthenticationException {
    public UserNotFoundException() {
        super("User Not Found");
    }
}
