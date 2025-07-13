package com.enotes.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enotes.entity.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {

    List<Subject> findAllByIsActiveTrue();

    Optional<Subject> findByIdAndIsDeletedFalse(Integer id);

    List<Subject> findByIsDeletedFalse();

    Boolean existsByNameAndSemesterId(String name, Integer semesterId); // Prevent duplicate subjects in a semester

    List<Subject> findBySemesterIdAndIsDeletedFalse(Integer semesterId); // Get all subjects of a semester
}
