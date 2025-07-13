package com.enotes.service.impl;

import com.enotes.dto.SubjectRequest;
import com.enotes.dto.SubjectResponse;
import com.enotes.entity.Semester;
import com.enotes.entity.Subject;
import com.enotes.exceptionhandling.ExistDataException;
import com.enotes.exceptionhandling.ResourceNotFoundException;
import com.enotes.repository.SemesterRepository;
import com.enotes.repository.SubjectRepository;
import com.enotes.service.CacheManagerService;
import com.enotes.service.SubjectService;
import com.enotes.util.Validation;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;

@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private SemesterRepository semesterRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private Validation validation;

    @Autowired
    private CacheManagerService cacheService;

    @Override
    public Boolean saveSubject(SubjectRequest subjectRequest) throws Exception {
        validation.subjectValidation(subjectRequest);

        boolean exists = subjectRepository.existsByNameAndSemesterId(
                subjectRequest.getName().trim(), subjectRequest.getSemesterId());

        if (exists && ObjectUtils.isEmpty(subjectRequest.getId())) {
            throw new ExistDataException("Subject already exists in this semester.");
        }

        Subject subject = mapper.map(subjectRequest, Subject.class);
        subject.setSemester(semesterRepository.findById(subjectRequest .getSemesterId())
                .orElseThrow(() -> new ResourceNotFoundException("Semester not found")));

        if (ObjectUtils.isEmpty(subject.getId())) {
            subject.setIsDeleted(false);
        } else {
            updateSubject(subject);
        }

        Subject saved = subjectRepository.save(subject);
        return !ObjectUtils.isEmpty(saved);
    }

    private void updateSubject(Subject subject) {
        Optional<Subject> existing = subjectRepository.findById(subject.getId());
        if (existing.isPresent()) {
            Subject exist = existing.get();
            subject.setCreatedBy(exist.getCreatedBy());
            subject.setCreatedOn(exist.getCreatedOn());
            subject.setIsDeleted(exist.getIsDeleted());
        }
    }

    @Override
    @Cacheable("allSubject")
    public List<SubjectResponse> getAllSubjects() {
        return subjectRepository.findByIsDeletedFalse().stream()
                .map(s -> mapper.map(s, SubjectResponse.class)).toList();
    }

    @Override
    @Cacheable("activeSubject")
    public List<SubjectResponse> getAllActiveSubjects() {
        return subjectRepository.findAllByIsActiveTrue().stream()
                .map(s -> mapper.map(s, SubjectResponse.class)).toList();
    }

    @Override
    @Cacheable(value = "getSubjectById", key = "#id")
    public SubjectResponse getSubjectById(Integer id) throws Exception {
        Subject subject = subjectRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found"));
        return mapper.map(subject, SubjectResponse.class);
    }

    @Override
    @CacheEvict(value = "getSubjectById", key = "#id")
    public Boolean deleteSubjectById(Integer id) {
        Optional<Subject> opt = subjectRepository.findById(id);
        if (opt.isPresent()) {
            Subject subject = opt.get();
            subject.setIsDeleted(true);
            subjectRepository.save(subject);
            cacheService.removeCacheById(List.of("allSubject", "activeSubject"));
            return true;
        }
        return false;
    }

    @Override
    public List<SubjectResponse> getSubjectsBySemesterId(Integer semesterId) {
        return subjectRepository.findBySemesterIdAndIsDeletedFalse(semesterId).stream()
                .map(s -> mapper.map(s, SubjectResponse.class)).toList();
    }
}
