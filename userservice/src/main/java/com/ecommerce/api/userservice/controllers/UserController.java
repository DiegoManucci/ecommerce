package com.ecommerce.api.userservice.controllers;

import com.ecommerce.api.userservice.config.security.utils.JwtTokenUtil;
import com.ecommerce.api.userservice.dtos.UserDto;
import com.ecommerce.api.userservice.models.UserModel;
import com.ecommerce.api.userservice.services.UserDetailsServiceImpl;
import com.ecommerce.api.userservice.services.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/user")
public class UserController {

    private final UserDetailsServiceImpl userDetailsService;

    private final UserService userService;

    private final JwtTokenUtil jwtTokenUtil;

    public UserController(UserDetailsServiceImpl userDetailsService, UserService userService, JwtTokenUtil jwtTokenUtil) {
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping ("/signup")
    public ResponseEntity<Object> saveUser(@RequestBody @Valid UserDto userDto){ // @Valid -> valida anotacoes do UserDto
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDto, userModel);
        return ResponseEntity.status(201).body(userService.save(userModel));
    }

    @PostMapping ("/signin")
    public ResponseEntity<Object> findUser(@RequestBody @Valid UserDto userDto) throws Exception {
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDto, userModel);

        try{
            userModel = userDetailsService.loadUserByEmail(userDto.getEmail());
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }

        String JwtToken = jwtTokenUtil.generateToken(new HashMap<>(), userModel.getEmail());

        return ResponseEntity.status(200).body(JwtToken);
    }
}
