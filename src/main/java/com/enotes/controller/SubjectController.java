package com.enotes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RestController;

import com.enotes.dto.SubjectRequest;
import com.enotes.dto.SubjectResponse;
import com.enotes.endpoint.SubjectControllerEndpoint;
import com.enotes.service.SubjectService;
import com.enotes.util.CommonUtil;

@RestController
public class SubjectController implements SubjectControllerEndpoint {

    @Autowired
    private SubjectService subjectService;

    @Override
    public ResponseEntity<?> saveSubject(SubjectRequest subjectRequest) throws Exception {
        Boolean saved = subjectService.saveSubject(subjectRequest);
        if (saved) {
            return CommonUtil.createBuildResponseMessage("Subject saved successfully", HttpStatus.CREATED);
        } else {
            return CommonUtil.createErrorResponseMessage("Subject not saved", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> getAllSubjects() {
        List<SubjectResponse> subjects = subjectService.getAllSubjects();
        if (CollectionUtils.isEmpty(subjects)) {
            return ResponseEntity.noContent().build();
        } else {
            return CommonUtil.createBuildResponse(subjects, HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<?> getActiveSubjectsBySemesterId(Integer semesterId) {
        List<SubjectResponse> subjects = subjectService.getAllActiveSubjectsBySemesterId(semesterId);
        if (CollectionUtils.isEmpty(subjects)) {
            return ResponseEntity.noContent().build();
        } else {
            return CommonUtil.createBuildResponse(subjects, HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<?> getSubjectById(Integer id) throws Exception {
        SubjectResponse subject = subjectService.getSubjectById(id);
        if (ObjectUtils.isEmpty(subject)) {
            return CommonUtil.createErrorResponseMessage("Subject not found", HttpStatus.NOT_FOUND);
        } else {
            return CommonUtil.createBuildResponse(subject, HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<?> deleteSubjectById(Integer id) {
        Boolean deleted = subjectService.deleteSubjectById(id);
        if (deleted) {
            return CommonUtil.createBuildResponseMessage("Subject deleted successfully", HttpStatus.OK);
        } else {
            return CommonUtil.createErrorResponseMessage("Subject not deleted", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> getSubjectsBySemesterId(Integer semesterId) throws Exception {
        List<SubjectResponse> subjects = subjectService.getSubjectsBySemesterId(semesterId);
        if (CollectionUtils.isEmpty(subjects)) {
            return ResponseEntity.noContent().build();
        } else {
            return CommonUtil.createBuildResponse(subjects, HttpStatus.OK);
        }
    }
}
