package com.enotes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enotes.entity.Todo;

public interface TodoRepository extends JpaRepository<Todo, Integer>{

	List<Todo> findByCreatedBy(Integer userId);

}
