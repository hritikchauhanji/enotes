package com.enotes.endpoint;

import static com.enotes.util.ConstantUtil.Role_User;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.enotes.dto.TodoDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Todo", description = "All the Todo Operation APIs")
@RequestMapping("/api/v1/todo")
public interface TodoControllerEndpoint {
	
	@Operation(summary = "Save Todo", tags = { "Notes" }, description = "Save Todo")
	@PostMapping
	@PreAuthorize(Role_User)
	public ResponseEntity<?> saveTodo(@RequestBody TodoDto todo) throws Exception;
	
	@Operation(summary = "Get Todo", tags = { "Notes" }, description = "Get Todo")
	@GetMapping("/get/{id}")
	@PreAuthorize(Role_User)
	public ResponseEntity<?> getTodoByuser(@PathVariable Integer id) throws Exception;
	
	@Operation(summary = "Get All Todo By User", tags = { "Notes" }, description = "Get All Todo By User")
	@GetMapping("/getAll")
	@PreAuthorize(Role_User)
	public ResponseEntity<?> getAllTodoByUser() throws Exception;
}
