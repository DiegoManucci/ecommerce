package com.api.ecommerce.userservice.controllers;

import com.api.ecommerce.userservice.config.security.EmailPasswordAuthenticationToken;
import com.api.ecommerce.userservice.config.security.utils.JwtTokenUtil;
import com.api.ecommerce.userservice.dtos.UserDto;
import com.api.ecommerce.userservice.models.UserModel;
import com.api.ecommerce.userservice.services.AuthenticationService;
import com.api.ecommerce.userservice.services.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService, AuthenticationManager authenticationManager, UserService userService, JwtTokenUtil jwtTokenUtil) {
        this.authenticationService = authenticationService;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> saveUser(@RequestBody @Valid UserDto userDto) { // @Valid -> valida anotacoes do UserDto
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDto, userModel);
        return ResponseEntity.status(201).body(userService.save(userModel));
    }

    @PostMapping(value = "/signin", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findUser(@RequestBody @Valid UserDto userDto) {

        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDto, userModel);

        // if not authenticated will throw exception and stop here
        authenticationManager.authenticate(new EmailPasswordAuthenticationToken(userModel.getEmail(), userModel.getPassword(), Boolean.FALSE));

        String jwtToken = jwtTokenUtil.generateToken(new HashMap<>(), userModel.getEmail());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", jwtToken);

        return ResponseEntity.ok()
                .headers(headers)
                .body("");
    }
}