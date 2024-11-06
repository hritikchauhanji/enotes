package com.enotes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enotes.entity.Category;
import com.enotes.service.Services;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
	
	@Autowired
	private Services services;

	@PostMapping("/save-category")
	ResponseEntity<?> saveCategory(@RequestBody Category category) {
		Boolean saveCategory = services.saveCategory(category);
		if(saveCategory) {
			return new ResponseEntity<>("saved", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("not saved", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping("/getAll")
	ResponseEntity<?> getAllCategory() {
		List<Category> getAll = services.getAllCategory();
		if(CollectionUtils.isEmpty(getAll)) {
			return ResponseEntity.noContent().build();
		} else {
			return new ResponseEntity<>(getAll, HttpStatus.OK);
		}
	}
}
