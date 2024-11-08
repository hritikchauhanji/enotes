package com.enotes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enotes.dto.CategoryDto;
import com.enotes.dto.CategoryResponse;
import com.enotes.entity.Category;
import com.enotes.service.Services;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
	
	@Autowired
	private Services services;

	@PostMapping("/save-category")
	ResponseEntity<?> saveCategory(@RequestBody CategoryDto categoryDto) {
		Boolean saveCategory = services.saveCategory(categoryDto);
		if(saveCategory) {
			return new ResponseEntity<>("saved", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("not saved", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping("/getAll")
	ResponseEntity<?> getAllCategory() {
		List<CategoryDto> getAll = services.getAllCategory();
		if(CollectionUtils.isEmpty(getAll)) {
			return ResponseEntity.noContent().build();
		} else {
			return new ResponseEntity<>(getAll, HttpStatus.OK);
		}
	}
	
	@GetMapping("/active")
	ResponseEntity<?> getActiveCategory() {
		List<CategoryResponse> getAll = services.getAllActiveCategory();
		if(CollectionUtils.isEmpty(getAll)) {
			return ResponseEntity.noContent().build();
		} else {
			return new ResponseEntity<>(getAll, HttpStatus.OK);
		}
	}
	
	@GetMapping("/{id}")
	ResponseEntity<?> getCategoryById(@PathVariable Integer id) {
		CategoryDto categoryDto = services.getCategoryById(id);
		if(ObjectUtils.isEmpty(categoryDto)) {
			return new ResponseEntity<>("Category not found by Id = "+id, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(categoryDto, HttpStatus.OK);
		}
	}
	
	@DeleteMapping("/{id}")
	ResponseEntity<?> deleteCategoryById(@PathVariable Integer id) {
		Boolean deleted = services.DeleteCategoryById(id);
		if(deleted) {
			return new ResponseEntity<>("Category delete successfully", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Category not deleted", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
