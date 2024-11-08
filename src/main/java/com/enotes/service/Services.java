package com.enotes.service;

import java.util.List;

import com.enotes.dto.CategoryDto;
import com.enotes.dto.CategoryResponse;
public interface Services {

	Boolean saveCategory(CategoryDto categoryDto);
	
	List<CategoryDto> getAllCategory();

	List<CategoryResponse> getAllActiveCategory();

	CategoryDto getCategoryById(Integer id);

	Boolean DeleteCategoryById(Integer id);
}