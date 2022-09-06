package com.api.ecommerce.userservice.handlers;

import com.api.ecommerce.userservice.controllers.UserController;
import com.api.ecommerce.userservice.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;

@ControllerAdvice(basePackageClasses = UserController.class)
public class UserExceptionHandler extends ResponseEntityExceptionHandler {

    private LinkedHashMap<Object, Object> _fillErrorMessages(String message){
        LinkedHashMap<Object, Object> body = new LinkedHashMap<>();

        body.put("timestamp", LocalDateTime.now());
        body.put("message", message);

        return body;
    }

    public ResponseEntity<Object> handleUserNotFound(UserNotFoundException e){
        LinkedHashMap<Object, Object> body = _fillErrorMessages(e.getMessage());
        return new ResponseEntity<>(body, HttpStatus.NO_CONTENT);
    }

}
