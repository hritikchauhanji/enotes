package com.enotes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enotes.entity.Todo;

public interface TodoRepository extends JpaRepository<Todo, Integer>{

}
