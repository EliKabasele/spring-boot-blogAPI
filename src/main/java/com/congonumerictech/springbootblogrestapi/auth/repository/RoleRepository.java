package com.congonumerictech.springbootblogrestapi.auth.repository;

import com.congonumerictech.springbootblogrestapi.auth.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
