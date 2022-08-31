package com.ecommerce.api.userservice.controllers;

import com.ecommerce.api.userservice.config.security.utils.JwtTokenUtil;
import com.ecommerce.api.userservice.dtos.UserDto;
import com.ecommerce.api.userservice.models.UserModel;
import com.ecommerce.api.userservice.services.AuthenticationService;
import com.ecommerce.api.userservice.services.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public AuthenticationController(AuthenticationService userDetailsService, AuthenticationManager authenticationManager, UserService userService, JwtTokenUtil jwtTokenUtil) {
        this.userDetailsService = userDetailsService;
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

    @PostMapping("/signin")
    public ResponseEntity<Object> findUser(@RequestBody @Valid UserDto userDto) {

        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDto, userModel);

        // if not authorized will throw exception and stop here
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userModel.getEmail(), userModel.getPassword()));

        UserDetails userDetails = userDetailsService.loadUserByUsername(userDto.getEmail());

        String jwtToken = jwtTokenUtil.generateToken(new HashMap<>(), userDetails.getUsername());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", jwtToken);

        return ResponseEntity.ok()
                .headers(headers)
                .body(jwtToken);
    }
}