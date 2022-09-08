package com.api.ecommerce.userservice.exceptions;

import org.springframework.security.core.AuthenticationException;

public class DuplicateEmailException extends AuthenticationException {
    public DuplicateEmailException() {
        super("Email already exists!");
    }
}
