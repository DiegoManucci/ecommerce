package com.api.ecommerce.userservice.repositories;

import com.api.ecommerce.userservice.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {
    public Boolean existsByEmail(String email);
}
