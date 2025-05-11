package com.enotes.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static com.enotes.util.ConstantUtil.Role_Admin;
import static com.enotes.util.ConstantUtil.Role_Admin_User;
import com.enotes.dto.CategoryDto;

@RequestMapping("/api/v1/category")
public interface CategoryControllerEndpoint {

	@PostMapping("/save")
	@PreAuthorize(Role_Admin)
	public ResponseEntity<?> saveCategory(@RequestBody CategoryDto categoryDto);
	
	@GetMapping("/")
	@PreAuthorize(Role_Admin)
	public ResponseEntity<?> getAllCategory();
	
	@GetMapping("/active")
	@PreAuthorize(Role_Admin_User)
	public ResponseEntity<?> getActiveCategory();
	
	@GetMapping("/{id}")
	@PreAuthorize(Role_Admin)
	public ResponseEntity<?> getCategoryById(@PathVariable Integer id) throws Exception;
	
	@DeleteMapping("/{id}")
	@PreAuthorize(Role_Admin)
	public ResponseEntity<?> deleteCategoryById(@PathVariable Integer id);
}
