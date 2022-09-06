package com.api.ecommerce.userservice.controllers;

import com.api.ecommerce.userservice.models.UserModel;
import com.api.ecommerce.userservice.services.UserService;
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
    public ResponseEntity<Object> getUser(@PathVariable(value = "id") UUID user_id){
        Optional<UserModel> userModel = userService.findById(user_id);
        return ResponseEntity.status(200).body(userModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable(value = "id") UUID user_id){

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> updateUser(@RequestBody UUID user_id){

    }

}
