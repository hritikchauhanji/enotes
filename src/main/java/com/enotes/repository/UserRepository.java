package com.enotes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enotes.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	Boolean existsByEmail(String email);

	User findByEmail(String email);

}
