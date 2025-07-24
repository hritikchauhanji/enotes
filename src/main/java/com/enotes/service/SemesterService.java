package com.enotes.service;

import java.util.List;

import com.enotes.dto.SemesterRequest;
import com.enotes.dto.SemesterResponse;

public interface SemesterService {

    Boolean saveSemester(SemesterRequest semesterRequest) throws Exception;

    List<SemesterResponse> getAllSemesters();

    List<SemesterResponse> getAllActiveSemestersByCourseId(Integer courseId);

    SemesterResponse getSemesterById(Integer id) throws Exception;

    Boolean deleteSemesterById(Integer id);

    List<SemesterResponse> getSemestersByCourseId(Integer courseId);
}
