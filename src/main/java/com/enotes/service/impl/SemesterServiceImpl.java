package com.enotes.service.impl;

import com.enotes.dto.SemesterRequest;
import com.enotes.dto.SemesterResponse;
import com.enotes.entity.Semester;
import com.enotes.exceptionhandling.ExistDataException;
import com.enotes.exceptionhandling.ResourceNotFoundException;
import com.enotes.repository.CourseRepository;
import com.enotes.repository.SemesterRepository;
import com.enotes.service.CacheManagerService;
import com.enotes.service.SemesterService;
import com.enotes.util.Validation;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;

@Service
public class SemesterServiceImpl implements SemesterService {

    @Autowired
    private SemesterRepository semesterRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private Validation validation;

    @Autowired
    private CacheManagerService cacheService;

    @Override
    public Boolean saveSemester(SemesterRequest semesterRequest) throws Exception {
        validation.semesterValidation(semesterRequest);

        boolean exists = semesterRepository.existsByNameAndCourseId(
                semesterRequest.getName().trim(), semesterRequest.getCourseId());



        Semester semester = mapper.map(semesterRequest, Semester.class);
        semester.setCourse(courseRepository.findById(semesterRequest.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found")));

        if (ObjectUtils.isEmpty(semester.getId())) {
            if (exists && ObjectUtils.isEmpty(semesterRequest.getId())) {
                throw new ExistDataException("Semester already exists for this course.");
            }
            semester.setIsDeleted(false);
        } else {
            updateSemester(semester);
        }

        Semester saved = semesterRepository.save(semester);
        return !ObjectUtils.isEmpty(saved);
    }

    private void updateSemester(Semester semester) {
        Optional<Semester> existing = semesterRepository.findById(semester.getId());
        if (existing.isPresent()) {
            Semester exist = existing.get();
            semester.setCreatedBy(exist.getCreatedBy());
            semester.setCreatedOn(exist.getCreatedOn());
            semester.setIsDeleted(false);
        }
    }

    @Override
//    @Cacheable("allSemester")
    public List<SemesterResponse> getAllSemesters() {
        return semesterRepository.findAll().stream()
                .map(s -> mapper.map(s, SemesterResponse.class)).toList();
    }

    @Override
//    @Cacheable("activeSemester")
    public List<SemesterResponse> getAllActiveSemestersByCourseId(Integer courseId) {
        return semesterRepository.findAllByIsActiveTrueAndCourseId(courseId).stream()
                .map(s -> mapper.map(s, SemesterResponse.class)).toList();
    }

    @Override
//    @Cacheable(value = "getSemesterById", key = "#id")
    public SemesterResponse getSemesterById(Integer id) throws  Exception {
        Semester semester = semesterRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Semester not found"));
        return mapper.map(semester, SemesterResponse.class);
    }

    @Override
//    @CacheEvict(value = "getSemesterById", key = "#id")
    public Boolean deleteSemesterById(Integer id) {
        Optional<Semester> opt = semesterRepository.findById(id);
        if (opt.isPresent()) {
            Semester semester = opt.get();
            semester.setIsDeleted(true);
            semester.setIsActive(false);
            semesterRepository.save(semester);
            cacheService.removeCacheById(List.of("allSemester", "activeSemester"));
            return true;
        }
        return false;
    }

    @Override
    public List<SemesterResponse> getSemestersByCourseId(Integer courseId) {
        return semesterRepository.findByCourseIdAndIsDeletedFalse(courseId).stream()
                .map(s -> mapper.map(s, SemesterResponse.class)).toList();
    }
}
