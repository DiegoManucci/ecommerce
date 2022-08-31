package com.ecommerce.api.userservice.handlers;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;

@ControllerAdvice
public class AuthorizationExceptionHandler extends ResponseEntityExceptionHandler {

    private LinkedHashMap<Object, Object> _fillErrorMessages(String message){
        LinkedHashMap<Object, Object> body = new LinkedHashMap<>();

        body.put("timestamp", LocalDateTime.now());
        body.put("message", message);

        return body;
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Object> handleExpiredTokenException(ExpiredJwtException e){
        LinkedHashMap<Object, Object> body = _fillErrorMessages(e.getMessage());
        return new ResponseEntity<>(body, HttpStatus.valueOf(401));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleInvalidTokenException(IllegalArgumentException e){
        LinkedHashMap<Object, Object> body = _fillErrorMessages(e.getMessage());
        return new ResponseEntity<>(body, HttpStatus.valueOf(400));
    }
}
