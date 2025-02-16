package com.enotes.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.enotes.dto.TodoDto;
import com.enotes.dto.TodoDto.StatusDto;
import com.enotes.entity.Todo;
import com.enotes.enums.TodoStatus;
import com.enotes.exceptionhandling.ResourceNotFoundException;
import com.enotes.repository.TodoRepository;
import com.enotes.service.TodoService;
import com.enotes.util.CommonUtil;
import com.enotes.util.Validation;

@Service
public class TodoServiceImpl implements TodoService {
	
	@Autowired
	private TodoRepository todoRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private Validation validation;

	@Override
	public Boolean saveTodo(TodoDto todo) throws Exception {
		//validation
		
		validation.todoValidation(todo);
		
		
		Todo saveTodo = mapper.map(todo, Todo.class);
		saveTodo.setStatusId(todo.getStatus().getId());
		Todo save = todoRepository.save(saveTodo);
		if(ObjectUtils.isEmpty(save)){
			return true;
		}
		return false;
	}

	@Override
	public TodoDto getTodoById(Integer id) throws Exception {
		Todo todo = todoRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Invalid Id..."));
		TodoDto getTodo = mapper.map(todo, TodoDto.class);
		setStatus(todo, getTodo);
		if(!ObjectUtils.isEmpty(getTodo)) {
			return getTodo;
		}
		return null;
	}

	private void setStatus(Todo todo, TodoDto getTodo) {
		for(TodoStatus st: TodoStatus.values()) {
			if(st.getId().equals(todo.getStatusId())) {
				StatusDto statusDto = StatusDto.builder().id(st.getId()).name(st.getName()).build();
				getTodo.setStatus(statusDto);
			}
		}
	}

	@Override
	public List<TodoDto> getAllTodoByUser() throws Exception {
		Integer userId = CommonUtil.getLoggedInUser().getId();
		List<Todo> allTodo = todoRepository.findByCreatedBy(userId);
		List<TodoDto> list = allTodo.stream().map(todo -> mapper.map(todo, TodoDto.class)).toList();
		if(!CollectionUtils.isEmpty(list)) {
			return list;
		}
		return null;
	}

	

}
