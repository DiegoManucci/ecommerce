package com.ecommerce.api.userservice.handlers;

import com.ecommerce.api.userservice.controllers.AuthenticationController;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;

@ControllerAdvice(basePackageClasses = AuthenticationController.class)
public class AuthorizationExceptionHandler extends ResponseEntityExceptionHandler {

    private LinkedHashMap<Object, Object> _fillErrorMessages(String message){
        LinkedHashMap<Object, Object> body = new LinkedHashMap<>();

        body.put("timestamp", LocalDateTime.now());
        body.put("message", message);

        return body;
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<Object> handleaccountDisabledException(DisabledException e){
        LinkedHashMap<Object, Object> body = _fillErrorMessages(e.getMessage());
        return new ResponseEntity<>(body, HttpStatus.valueOf(401));
    }

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<Object> handleaccountLockedException(LockedException e){
        LinkedHashMap<Object, Object> body = _fillErrorMessages(e.getMessage());
        return new ResponseEntity<>(body, HttpStatus.valueOf(401));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException e){
        LinkedHashMap<Object, Object> body = _fillErrorMessages(e.getMessage());
        return new ResponseEntity<>(body, HttpStatus.valueOf(401));
    }
}
