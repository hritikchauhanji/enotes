package com.enotes.service;

import java.util.List;

import com.enotes.dto.SubjectRequest;
import com.enotes.dto.SubjectResponse;

public interface SubjectService {

    Boolean saveSubject(SubjectRequest subjectRequest) throws Exception;

    List<SubjectResponse> getAllSubjects();

    List<SubjectResponse> getAllActiveSubjectsBySemesterId(Integer semesterId);

    SubjectResponse getSubjectById(Integer id) throws Exception;

    Boolean deleteSubjectById(Integer id);

    List<SubjectResponse> getSubjectsBySemesterId(Integer semesterId);
}
