package com.enotes.endpoint;

import static com.enotes.util.ConstantUtil.Role_Admin;
import static com.enotes.util.ConstantUtil.Role_Admin_User;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.enotes.dto.SemesterRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Semester", description = "All Semester-related API operations")
@RequestMapping("/api/v1/semester")
public interface SemesterControllerEndpoint {

    @Operation(summary = "Save Semester", tags = {"Semester"}, description = "Admin can save a new semester")
    @PostMapping("/save")
    @PreAuthorize(Role_Admin)
    ResponseEntity<?> saveSemester(@RequestBody SemesterRequest semesterRequest) throws Exception;

    @Operation(summary = "Get All Semesters", tags = {"Semester"}, description = "Admin can get all semesters")
    @GetMapping("/")
    @PreAuthorize(Role_Admin)
    ResponseEntity<?> getAllSemesters();

    @Operation(summary = "Get All Active Semesters", tags = {"Semester"}, description = "Admin and User can view active semesters")
    @GetMapping("/active")
    @PreAuthorize(Role_Admin_User)
    ResponseEntity<?> getActiveSemesters();

    @Operation(summary = "Get Semester By ID", tags = {"Semester"}, description = "Admin can view semester details by ID")
    @GetMapping("/{id}")
    @PreAuthorize(Role_Admin)
    ResponseEntity<?> getSemesterById(@PathVariable Integer id) throws Exception;

    @Operation(summary = "Delete Semester By ID", tags = {"Semester"}, description = "Admin can soft-delete a semester by ID")
    @DeleteMapping("/{id}")
    @PreAuthorize(Role_Admin)
    ResponseEntity<?> deleteSemesterById(@PathVariable Integer id);

    @Operation(summary = "Get Semesters By Course ID", tags = {"Semester"}, description = "Admin and User can get semesters by Course ID")
    @GetMapping("/course/{courseId}")
    @PreAuthorize(Role_Admin_User)
    ResponseEntity<?> getSemestersByCourseId(@PathVariable Integer courseId) throws Exception;
}
