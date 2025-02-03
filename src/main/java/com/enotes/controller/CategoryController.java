package com.enotes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.enotes.service.Services;
import com.enotes.util.CommonUtil;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

	@Autowired
	private Services services;

	@PostMapping("/")
	@PreAuthorize("hasRole('ADMIN')")
	ResponseEntity<?> saveCategory(@RequestBody CategoryDto categoryDto) {
		Boolean saveCategory = services.saveCategory(categoryDto);
		if (saveCategory) {
			return CommonUtil.createBuildResponseMessage("Category saved", HttpStatus.CREATED);
//			return new ResponseEntity<>("saved", HttpStatus.CREATED);
		} else {
//			return new ResponseEntity<>("not saved", HttpStatus.INTERNAL_SERVER_ERROR);
			return CommonUtil.createErrorResponseMessage("Category not saved", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getAll")
	@PreAuthorize("hasRole('ADMIN')")
	ResponseEntity<?> getAllCategory() {
		List<CategoryDto> getAll = services.getAllCategory();
		if (CollectionUtils.isEmpty(getAll)) {
			return ResponseEntity.noContent().build();
		} else {
			return CommonUtil.createBuildResponse(getAll, HttpStatus.OK);
//			return new ResponseEntity<>(getAll, HttpStatus.OK);
		}
	}

	@GetMapping("/active")
	ResponseEntity<?> getActiveCategory() {
		List<CategoryResponse> getAll = services.getAllActiveCategory();
		if (CollectionUtils.isEmpty(getAll)) {
			return ResponseEntity.noContent().build();
		} else {
//			return new ResponseEntity<>(getAll, HttpStatus.OK);
			return CommonUtil.createBuildResponse(getAll, HttpStatus.OK);
		}
	}

	@GetMapping("/{id}")
	ResponseEntity<?> getCategoryById(@PathVariable Integer id) throws Exception {
		CategoryDto categoryDto = services.getCategoryById(id);
		if (ObjectUtils.isEmpty(categoryDto)) {
//			return new ResponseEntity<>("Interval Server Error", HttpStatus.NOT_FOUND);
			return CommonUtil.createErrorResponseMessage("Internal server error", HttpStatus.NOT_FOUND);
		} else {
//			return new ResponseEntity<>(categoryDto, HttpStatus.OK);
			return CommonUtil.createBuildResponse(categoryDto, HttpStatus.OK);
		}
//		try {
//			CategoryDto categoryDto = services.getCategoryById(id);
//			if (ObjectUtils.isEmpty(categoryDto)) {
//				return new ResponseEntity<>("Category not found by Id = " + id, HttpStatus.NOT_FOUND);
//			} else {
//				return new ResponseEntity<>(categoryDto, HttpStatus.OK);
//			}
//		} catch (ResourceNotFoundException e) {
//			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//		} catch (Exception e) {
//			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//		}

	}

	@DeleteMapping("/{id}")
	ResponseEntity<?> deleteCategoryById(@PathVariable Integer id) {
		Boolean deleted = services.DeleteCategoryById(id);
		if (deleted) {
//			return new ResponseEntity<>("Category delete successfully", HttpStatus.OK);
			return CommonUtil.createBuildResponseMessage("Category deleted successfully", HttpStatus.OK);
		} else {
//			return new ResponseEntity<>("Category not deleted", HttpStatus.INTERNAL_SERVER_ERROR);
			return CommonUtil.createErrorResponseMessage("Category not deleted", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
