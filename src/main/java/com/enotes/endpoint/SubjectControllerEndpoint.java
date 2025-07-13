package com.enotes.endpoint;

import static com.enotes.util.ConstantUtil.Role_Admin;
import static com.enotes.util.ConstantUtil.Role_Admin_User;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.enotes.dto.SubjectRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Subject", description = "All Subject-related API operations")
@RequestMapping("/api/v1/subject")
public interface SubjectControllerEndpoint {

    @Operation(summary = "Save Subject", tags = {"Subject"}, description = "Admin can save or update a subject")
    @PostMapping("/save")
    @PreAuthorize(Role_Admin)
    ResponseEntity<?> saveSubject(@RequestBody SubjectRequest subjectRequest) throws Exception;

    @Operation(summary = "Get All Subjects", tags = {"Subject"}, description = "Admin can fetch all subjects")
    @GetMapping("/")
    @PreAuthorize(Role_Admin)
    ResponseEntity<?> getAllSubjects();

    @Operation(summary = "Get All Active Subjects", tags = {"Subject"}, description = "Admin and User can view active subjects")
    @GetMapping("/active")
    @PreAuthorize(Role_Admin_User)
    ResponseEntity<?> getActiveSubjects();

    @Operation(summary = "Get Subject By ID", tags = {"Subject"}, description = "Admin can fetch subject details by ID")
    @GetMapping("/{id}")
    @PreAuthorize(Role_Admin)
    ResponseEntity<?> getSubjectById(@PathVariable Integer id) throws Exception;

    @Operation(summary = "Delete Subject By ID", tags = {"Subject"}, description = "Admin can soft-delete a subject by ID")
    @DeleteMapping("/{id}")
    @PreAuthorize(Role_Admin)
    ResponseEntity<?> deleteSubjectById(@PathVariable Integer id);

    @Operation(summary = "Get Subjects By Semester ID", tags = {"Subject"}, description = "Admin and User can get subjects by SemesterCourse ID")
    @GetMapping("/semester/{semesterId}")
    @PreAuthorize(Role_Admin_User)
    ResponseEntity<?> getSubjectsBySemesterId(@PathVariable Integer semesterId) throws Exception;

}
