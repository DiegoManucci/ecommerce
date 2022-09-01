package com.ecommerce.api.userservice.repositories;

import com.ecommerce.api.userservice.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository  extends JpaRepository<UserModel, UUID> {
    Optional<UserModel> findByEmail(String email);

    Boolean existsByEmail(String email);
}
