package com.api.ecommerce.userservice.controllers;

import com.api.ecommerce.userservice.dtos.UserDto;
import com.api.ecommerce.userservice.models.UserModel;
import com.api.ecommerce.userservice.services.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin("*")
@RequestMapping("/user")
public class UserController implements Serializable {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUser(@PathVariable(value = "id") UUID userId){
        UserModel userModel = userService.findById(userId).get();
        return ResponseEntity.status(200).body(userModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable(value = "id") UUID userId, @RequestBody UserDto userDto){
        UserModel userModel = userService.findById(userId).get();
        BeanUtils.copyProperties(userDto, userModel);
        return ResponseEntity.status(201).body(userService.save(userModel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "id") UUID userId){
        UserModel userModel = userService.findById(userId).get();
        userService.delete(userModel);
        return ResponseEntity.status(200).body("User Deleted!");
    }

}
