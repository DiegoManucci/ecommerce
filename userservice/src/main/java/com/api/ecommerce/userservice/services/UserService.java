package com.api.ecommerce.userservice.services;

import com.api.ecommerce.userservice.exceptions.UserNotFoundException;
import com.api.ecommerce.userservice.models.UserModel;
import com.api.ecommerce.userservice.repositories.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public Optional<UserModel> findById(UUID user_id){
        if(userRepository.findById(user_id).isEmpty()){
            throw new UserNotFoundException();
        }
        return userRepository.findById(user_id);
    }

    @Transactional
    public UserModel save(UserModel userModel){
        return userRepository.save(userModel);
    }
}
