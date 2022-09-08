package com.api.ecommerce.userservice.services;

import com.api.ecommerce.userservice.exceptions.DuplicateEmailException;
import com.api.ecommerce.userservice.exceptions.UserNotFoundException;
import com.api.ecommerce.userservice.models.UserModel;
import com.api.ecommerce.userservice.repositories.AuthenticationRepository;
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

    public Optional<UserModel> findById(UUID userId){
        if(userRepository.findById(userId).isEmpty()){
            throw new UserNotFoundException();
        }
        return userRepository.findById(userId);
    }

    @Transactional
    public UserModel save(UserModel userModel){
        if(userRepository.existsByEmail(userModel.getEmail())){
            throw new DuplicateEmailException();
        }
        return userRepository.save(userModel);
    }

    @Transactional
    public void delete(UserModel userModel){
        userRepository.delete(userModel);
    }
}
