package com.enotes.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.enotes.dto.TodoDto;

@RequestMapping("/api/v1/todo")
public interface TodoControllerEndpoint {
	
	@PostMapping
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> saveTodo(@RequestBody TodoDto todo) throws Exception;
	
	@GetMapping("/get/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getTodoByuser(@PathVariable Integer id) throws Exception;
	
	@GetMapping("/getAll")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getAllTodoByUser() throws Exception;
}
