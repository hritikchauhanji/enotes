package com.enotes.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enotes.entity.Semester;

public interface SemesterRepository extends JpaRepository<Semester, Integer> {

    List<Semester> findAllByIsActiveTrue();

    Optional<Semester> findByIdAndIsDeletedFalse(Integer id);

    List<Semester> findByIsDeletedFalse();

    Boolean existsByNameAndCourseId(String name, Integer courseId); // To avoid duplicates in same course

    List<Semester> findByCourseIdAndIsDeletedFalse(Integer courseId); // Get all semesters of a course
}
