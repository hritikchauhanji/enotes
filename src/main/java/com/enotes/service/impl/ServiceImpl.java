package com.enotes.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.stereotype.Service;

import com.enotes.entity.Category;
import com.enotes.repository.CategoryRepository;
import com.enotes.service.Services;

@Service
public class ServiceImpl implements Services {
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public Boolean saveCategory(Category category) {
		category.setIsDeleted(false);
		category.setCreatedOn(new Date());
		category.setUpdatedOn(new Date());
		Category save = categoryRepository.save(category);
		if(ObjectUtils.isEmpty(save)) {
			return false;
		}
		return true;
	}

	@Override
	public List<Category> getAllCategory() {
		List<Category> all = categoryRepository.findAll();
		return all;
	}

}
