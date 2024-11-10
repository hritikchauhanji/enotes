package com.enotes.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.stereotype.Service;

import com.enotes.dto.CategoryDto;
import com.enotes.dto.CategoryResponse;
import com.enotes.entity.Category;
import com.enotes.exceptionhandling.ResourceNotFoundException;
import com.enotes.repository.CategoryRepository;
import com.enotes.service.Services;

@Service
public class ServiceImpl implements Services {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper mapper;

	@Override
	public Boolean saveCategory(CategoryDto categoryDto) {
//		Category category = new Category();
//		category.setName(categoryDto.getName());
//		category.setDescription(categoryDto.getDescription());
//		category.setIsActive(categoryDto.getIsActive());
//		category.setCreatedBy(categoryDto.getCreatedBy());
//		category.setUpdatedBy(categoryDto.getUpdatedBy());
		Category category = mapper.map(categoryDto, Category.class);
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
	public List<CategoryDto> getAllCategory() {
		List<Category> categories = categoryRepository.findByIsDeletedFalse();
		List<CategoryDto> categoriesDto = categories.stream().map(cat -> mapper.map(cat, CategoryDto.class)).toList();
		return categoriesDto;
	}

	@Override
	public List<CategoryResponse> getAllActiveCategory() {
		List<Category> categories = categoryRepository.findAllByIsActiveTrue();
		List<CategoryResponse> categoriesResponse = categories.stream().map(cat -> mapper.map(cat, CategoryResponse.class)).toList();
		return categoriesResponse;
	}

	@Override
	public CategoryDto getCategoryById(Integer id) throws Exception {
		Category category = categoryRepository.findByIdAndIsDeletedFalse(id).orElseThrow(() -> new ResourceNotFoundException("Category not found by id = " + id));
		if(!ObjectUtils.isEmpty(category)) {
//			if(category.getName() == null) {
//				throw new IllegalArgumentException("name is null");
//			}
			
//			category.getName().toUpperCase();
				return mapper.map(category, CategoryDto.class);
		}

		return null;
	}

	@Override
	public Boolean DeleteCategoryById(Integer id) {
		Optional<Category> findByCategory = categoryRepository.findById(id);
		if(findByCategory.isPresent()) {
			Category category = findByCategory.get();
			category.setIsDeleted(true);
			categoryRepository.save(category);
			return true;
		}
		return false;
	}

}
