package com.enotes.endpoint;

import static com.enotes.util.ConstantUtil.Role_Admin;
import static com.enotes.util.ConstantUtil.Role_Admin_User;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.enotes.dto.CategoryDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Category", description = "All the category operation APIs")
@RequestMapping("/api/v1/category")
public interface CategoryControllerEndpoint {

	@Operation(summary = "Save Category", tags = {"Category"}, description = "Admin Save Category")
	@PostMapping("/save")
	@PreAuthorize(Role_Admin)
	public ResponseEntity<?> saveCategory(@RequestBody CategoryDto categoryDto);
	
	@Operation(summary = "Get All Category", tags = {"Category"}, description = "Admin Get All Category")
	@GetMapping("/")
	@PreAuthorize(Role_Admin)
	public ResponseEntity<?> getAllCategory();
	
	@Operation(summary = "Get Active", tags = {"Category"}, description = "Admin & User Get Active Category")
	@GetMapping("/active")
	@PreAuthorize(Role_Admin_User)
	public ResponseEntity<?> getActiveCategory();
	
	@Operation(summary = "Get Category By Id", tags = {"Category"}, description = "Admin Get Category Details")
	@GetMapping("/{id}")
	@PreAuthorize(Role_Admin)
	public ResponseEntity<?> getCategoryById(@PathVariable Integer id) throws Exception;
	
	@Operation(summary = "Delete Category By Id", tags = {"Category"}, description = "Admin Delete Category")
	@DeleteMapping("/{id}")
	@PreAuthorize(Role_Admin)
	public ResponseEntity<?> deleteCategoryById(@PathVariable Integer id);
}
