package com.enotes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enotes.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
