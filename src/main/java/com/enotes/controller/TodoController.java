package com.enotes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enotes.dto.TodoDto;
import com.enotes.endpoint.TodoControllerEndpoint;
import com.enotes.service.TodoService;
import com.enotes.util.CommonUtil;

@RestController
public class TodoController implements TodoControllerEndpoint {

	@Autowired
	private TodoService todoService;
	
	@Override
	public ResponseEntity<?> saveTodo(@RequestBody TodoDto todo) throws Exception{
		Boolean saveTodo = todoService.saveTodo(todo);
		if(!ObjectUtils.isEmpty(saveTodo)) {
			return CommonUtil.createBuildResponseMessage("Todo Saved", HttpStatus.CREATED); 	
		}
		return CommonUtil.createErrorResponseMessage("Todo Not Save", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@Override
	public ResponseEntity<?> getTodoByuser(@PathVariable Integer id) throws Exception{
		TodoDto todoById = todoService.getTodoById(id);
		if(!ObjectUtils.isEmpty(todoById)) {
			return CommonUtil.createBuildResponse(todoById, HttpStatus.OK);
		}
		return CommonUtil.createErrorResponseMessage("Todo is not found", HttpStatus.NOT_FOUND);
	}
	
	@Override
	public ResponseEntity<?> getAllTodoByUser() throws Exception{
		List<TodoDto> todoById = todoService.getAllTodoByUser();
		if(!CollectionUtils.isEmpty(todoById)) {
			return CommonUtil.createBuildResponse(todoById, HttpStatus.OK);
		}
		return ResponseEntity.noContent().build();
	}
	
	
}
