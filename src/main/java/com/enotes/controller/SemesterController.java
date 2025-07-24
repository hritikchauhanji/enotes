package com.enotes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RestController;

import com.enotes.dto.SemesterRequest;
import com.enotes.dto.SemesterResponse;
import com.enotes.endpoint.SemesterControllerEndpoint;
import com.enotes.service.SemesterService;
import com.enotes.util.CommonUtil;

@RestController
public class SemesterController implements SemesterControllerEndpoint {

    @Autowired
    private SemesterService semesterService;

    @Override
    public ResponseEntity<?> saveSemester(SemesterRequest semesterRequest) throws Exception {
        Boolean saved = semesterService.saveSemester(semesterRequest);
        if (saved) {
            return CommonUtil.createBuildResponseMessage("Semester saved successfully", HttpStatus.CREATED);
        } else {
            return CommonUtil.createErrorResponseMessage("Semester not saved", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> getAllSemesters() {
        List<SemesterResponse> semesters = semesterService.getAllSemesters();
        if (CollectionUtils.isEmpty(semesters)) {
            return ResponseEntity.noContent().build();
        } else {
            return CommonUtil.createBuildResponse(semesters, HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<?> getActiveSemestersByCourseId(Integer courseId) {
        List<SemesterResponse> semesters = semesterService.getAllActiveSemestersByCourseId(courseId);
        if (CollectionUtils.isEmpty(semesters)) {
            return ResponseEntity.noContent().build();
        } else {
            return CommonUtil.createBuildResponse(semesters, HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<?> getSemesterById(Integer id) throws Exception {
        SemesterResponse semester = semesterService.getSemesterById(id);
        if (ObjectUtils.isEmpty(semester)) {
            return CommonUtil.createErrorResponseMessage("Semester not found", HttpStatus.NOT_FOUND);
        } else {
            return CommonUtil.createBuildResponse(semester, HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<?> deleteSemesterById(Integer id) {
        Boolean deleted = semesterService.deleteSemesterById(id);
        if (deleted) {
            return CommonUtil.createBuildResponseMessage("Semester deleted successfully", HttpStatus.OK);
        } else {
            return CommonUtil.createErrorResponseMessage("Semester not deleted", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> getSemestersByCourseId(Integer courseId) throws Exception {
        List<SemesterResponse> semesters = semesterService.getSemestersByCourseId(courseId);
        if (CollectionUtils.isEmpty(semesters)) {
            return ResponseEntity.noContent().build();
        } else {
            return CommonUtil.createBuildResponse(semesters, HttpStatus.OK);
        }
    }
}
