package com.congonumerictech.springbootblogrestapi.auth.repository;

import com.congonumerictech.springbootblogrestapi.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUserName(String userName);

    Optional<User> findByEmailOrUserName(String email, String userName);

    Boolean existsByEmail(String email);

    Boolean existsByUserName( String userName);
}
