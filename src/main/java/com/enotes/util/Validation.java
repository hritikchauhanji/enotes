package com.enotes.util;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.enotes.dto.CategoryDto;
import com.enotes.exceptionhandling.ValidationException;

@Component
public class Validation {
	
	public void categoryValidation(CategoryDto categoryDto) {
		Map<String, Object> error = new LinkedHashMap<>();
		
		if(ObjectUtils.isEmpty(categoryDto)) {
			throw new IllegalArgumentException("Category Object/JSON shouldn't be null or empty");
		} else {
			// validation of name :-
			if(ObjectUtils.isEmpty(categoryDto.getName())) {
				error.put("name", "Name shouldn't be null or empty");
			} else  {
				if(categoryDto.getName().length()<10) {
					error.put("name", "Name shouldn't be less than 10");
				} 
				if(categoryDto.getName().length()>100) {
					error.put("name", "Name shouldn't be greater than 100");
				}
			}
			
			// validation of description :-
			if(ObjectUtils.isEmpty(categoryDto.getDescription())) {
				error.put("description", "Description shouldn't be null or empty");
			}
			
			// validation of Active :-
			if(ObjectUtils.isEmpty(categoryDto.getIsActive())) {
				error.put("isActive", "isActive shouldn't be null or empty");
			} else  {
				if(categoryDto.getIsActive() != Boolean.TRUE && categoryDto.getIsActive() != Boolean.FALSE) {
					error.put("isActive", "invalid value isActive field");
				}
			}
		}
		
		if(!error.isEmpty()) {
			throw new ValidationException(error);
		}
	}
}
