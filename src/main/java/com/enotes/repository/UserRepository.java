package com.enotes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enotes.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer>{

	Boolean existsByEmail(String email);

	User findByEmail(String email);

	@Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = :roleName")
	List<User> findAllByRolesName(@Param("roleName") String roleName);


}
