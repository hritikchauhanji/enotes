package com.enotes.service;

import java.util.List;

import com.enotes.entity.Category;

public interface Services {

	Boolean saveCategory(Category category);
	
	List<Category> getAllCategory();
}
