package com.enotes.endpoint;

import static com.enotes.util.ConstantUtil.Role_Admin;
import static com.enotes.util.ConstantUtil.Role_Admin_User;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.enotes.dto.CourseRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Course", description = "All Course-related API operations")
@RequestMapping("/api/v1/course")
public interface CourseControllerEndpoint {

	@Operation(summary = "Save Course", tags = {"Course"}, description = "Admin can save a new course")
	@PostMapping("/save")
	@PreAuthorize(Role_Admin)
	ResponseEntity<?> saveCourse(@RequestBody CourseRequest courseRequest);

	@Operation(summary = "Get All Courses", tags = {"Course"}, description = "Admin can get all courses")
	@GetMapping("/")
	@PreAuthorize(Role_Admin)
	ResponseEntity<?> getAllCourses();

	@Operation(summary = "Get All Active Courses", tags = {"Course"}, description = "Admin and User can view active courses")
	@GetMapping("/active")
//	@PreAuthorize(Role_Admin_User)
	ResponseEntity<?> getActiveCourses();

	@Operation(summary = "Get Course By ID", tags = {"Course"}, description = "Admin can view specific course details")
	@GetMapping("/{id}")
	@PreAuthorize(Role_Admin)
	ResponseEntity<?> getCourseById(@PathVariable Integer id) throws Exception;

	@Operation(summary = "Delete Course By ID", tags = {"Course"}, description = "Admin can soft-delete a course by ID")
	@DeleteMapping("/{id}")
	@PreAuthorize(Role_Admin)
	ResponseEntity<?> deleteCourseById(@PathVariable Integer id);
}
