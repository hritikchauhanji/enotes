package com.enotes.service;

import java.util.List;

import com.enotes.dto.TodoDto;

public interface TodoService {

	Boolean saveTodo(TodoDto todo) throws Exception;
	
	TodoDto getTodoById(Integer id) throws Exception;
	
	List<TodoDto> getAllTodoByUser() throws Exception;
}
