package com.ecommerce.api.userservice.exceptions;

import org.springframework.security.core.AuthenticationException;

public class InvalidPasswordException extends AuthenticationException {
    public InvalidPasswordException(){
        super("Invalid Password");
    }
}
