package com.enotes.util;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.enotes.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.enotes.dto.TodoDto.StatusDto;
import com.enotes.enums.TodoStatus;
import com.enotes.exceptionhandling.ExistDataException;
import com.enotes.exceptionhandling.ResourceNotFoundException;
import com.enotes.exceptionhandling.ValidationException;
import com.enotes.repository.RoleRepository;
import com.enotes.repository.UserRepository;

@Component
public class Validation {
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public void courseValidation(CourseRequest courseRequest) {
		Map<String, Object> error = new LinkedHashMap<>();
		
		if(ObjectUtils.isEmpty(courseRequest)) {
			throw new IllegalArgumentException("Category Object/JSON shouldn't be null or empty");
		} else {
			// validation of name :-
			if(ObjectUtils.isEmpty(courseRequest.getName())) {
				error.put("name", "Name shouldn't be null or empty");
			} else  {
				if(courseRequest.getName().length()<3) {
					error.put("name", "Name shouldn't be less than 3");
				} 
				if(courseRequest.getName().length()>100) {
					error.put("name", "Name shouldn't be greater than 100");
				}
			}
			
			// validation of description :-
			if(ObjectUtils.isEmpty(courseRequest.getDescription())) {
				error.put("description", "Description shouldn't be null or empty");
			}
			
			// validation of Active :-
			if(ObjectUtils.isEmpty(courseRequest.getIsActive())) {
				error.put("isActive", "isActive shouldn't be null or empty");
			} else  {
				if(courseRequest.getIsActive() != Boolean.TRUE && courseRequest.getIsActive() != Boolean.FALSE) {
					error.put("isActive", "invalid value isActive field");
				}
			}
		}
		
		if(!error.isEmpty()) {
			throw new ValidationException(error);
		}
	}

	public void semesterValidation(SemesterRequest semesterRequest) {
		Map<String, Object> error = new LinkedHashMap<>();

		if (ObjectUtils.isEmpty(semesterRequest)) {
			throw new IllegalArgumentException("Semester object/JSON shouldn't be null or empty");
		} else {
			// Validate name
			if (ObjectUtils.isEmpty(semesterRequest.getName())) {
				error.put("name", "Name shouldn't be null or empty");
			} else {
				if (semesterRequest.getName().length() < 3) {
					error.put("name", "Name shouldn't be less than 3 characters");
				}
				if (semesterRequest.getName().length() > 100) {
					error.put("name", "Name shouldn't be greater than 100 characters");
				}
			}

			// Validate isActive
			if (ObjectUtils.isEmpty(semesterRequest.getIsActive())) {
				error.put("isActive", "isActive shouldn't be null or empty");
			} else {
				if (semesterRequest.getIsActive() != Boolean.TRUE && semesterRequest.getIsActive() != Boolean.FALSE) {
					error.put("isActive", "Invalid value for isActive field");
				}
			}

			// Validate courseId
			if (ObjectUtils.isEmpty(semesterRequest.getCourseId())) {
				error.put("courseId", "CourseId shouldn't be null or empty");
			}
		}

		if (!error.isEmpty()) {
			throw new ValidationException(error);
		}
	}

	public void subjectValidation(SubjectRequest subjectRequest) {
		Map<String, Object> error = new LinkedHashMap<>();

		if (ObjectUtils.isEmpty(subjectRequest)) {
			throw new IllegalArgumentException("Subject object/JSON shouldn't be null or empty");
		} else {
			// Validate name
			if (ObjectUtils.isEmpty(subjectRequest.getName())) {
				error.put("name", "Name shouldn't be null or empty");
			} else {
				if (subjectRequest.getName().length() < 3) {
					error.put("name", "Name shouldn't be less than 3 characters");
				}
				if (subjectRequest.getName().length() > 100) {
					error.put("name", "Name shouldn't be greater than 100 characters");
				}
			}

			// Validate isActive
			if (ObjectUtils.isEmpty(subjectRequest.getIsActive())) {
				error.put("isActive", "isActive shouldn't be null or empty");
			} else {
				if (subjectRequest.getIsActive() != Boolean.TRUE && subjectRequest.getIsActive() != Boolean.FALSE) {
					error.put("isActive", "Invalid value for isActive field");
				}
			}

			// Validate semesterId
			if (ObjectUtils.isEmpty(subjectRequest.getSemesterId())) {
				error.put("semesterId", "SemesterId shouldn't be null or empty");
			}
		}

		if (!error.isEmpty()) {
			throw new ValidationException(error);
		}
	}



	public void todoValidation(TodoDto todo) throws Exception {
		StatusDto statusDto = todo.getStatus();
		
		Boolean statusFound = false;
		
		for(TodoStatus st: TodoStatus.values()) {
			if(st.getId().equals(statusDto.getId())) {
				statusFound = true;
			}
		}
		
		if(!statusFound) {
			throw new ResourceNotFoundException("Invalid Status");
		}
	}
	
	public void userRegisterValidation(UserRequest userRequest) throws Exception{
		
		
		// validation of firstname:-
		if(!StringUtils.hasText(userRequest.getFirstName())) {
			throw new IllegalArgumentException("First Name is Invalid...");
		}
		
		if(!StringUtils.hasText(userRequest.getLastName())) {
			throw new IllegalArgumentException("Last Name is Invalid...");
		}
		
		if(!StringUtils.hasText(userRequest.getEmail()) && !userRequest.getEmail().matches(ConstantUtil.Email_Regex)) {
			throw new IllegalArgumentException("Email is Invalid...");
		} else {
			Boolean existsByEmail = userRepository.existsByEmail(userRequest.getEmail());
			if(existsByEmail) {
				throw new ExistDataException("Email is already registered");
			}
		}
		
		if(!StringUtils.hasText(userRequest.getMobNo()) && !userRequest.getEmail().matches(ConstantUtil.Mobile_No_Regex)) {
			throw new IllegalArgumentException("Mobile No is Invalid...");
		}
		
		if(!StringUtils.hasText(userRequest.getPassword()) && !userRequest.getPassword().matches(ConstantUtil.Password_Regex)) {
			throw new IllegalArgumentException("Password is Invalid...");
		}
		
		if(CollectionUtils.isEmpty(userRequest.getRoles())) {
			throw new IllegalArgumentException("Roles are Invalid...");
		} else {
			List<Integer> roleIds = roleRepository.findAll().stream().map(r->r.getId()).toList();
			
			List<Integer> reqRoleIds =  userRequest.getRoles().stream().map(r->r.getId()).filter(roleId -> !roleIds.contains(roleId)).toList();
			
			if(!CollectionUtils.isEmpty(reqRoleIds)) {
				throw new IllegalArgumentException("Roles are Invalid = " + reqRoleIds);
			}
		}
	}
}
