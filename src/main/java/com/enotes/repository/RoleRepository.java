package com.enotes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enotes.entity.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(String user);
}
