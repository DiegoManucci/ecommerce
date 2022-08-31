package com.ecommerce.api.userservice.controllers;

import com.ecommerce.api.userservice.config.security.EmailPasswordAuthenticationToken;
import com.ecommerce.api.userservice.config.security.utils.JwtTokenUtil;
import com.ecommerce.api.userservice.dtos.UserDto;
import com.ecommerce.api.userservice.models.UserModel;
import com.ecommerce.api.userservice.services.UserDetailsServiceImpl;
import com.ecommerce.api.userservice.services.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public AuthenticationController(UserDetailsServiceImpl userDetailsService, AuthenticationManager authenticationManager, UserService userService, JwtTokenUtil jwtTokenUtil) {
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
    public ResponseEntity<Object> findUser(@RequestBody @Valid UserDto userDto) throws Exception {

        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDto, userModel);

        if()

        authenticationManager.authenticate(new EmailPasswordAuthenticationToken(userModel.getEmail(), userModel.getPassword()));

        UserDetails userDetails = userDetailsService.loadUserByUsername(userDto.getEmail());

        String jwtToken = jwtTokenUtil.generateToken(new HashMap<>(), userDetails.getUsername());

        return ResponseEntity.ok(jwtToken);
    }
}