package com.enotes.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enotes.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Integer> {

	List<Course> findAllByIsActiveTrue();

	Optional<Course> findByIdAndIsDeletedFalse(Integer id);

	List<Course> findByIsDeletedFalse();

	Boolean existsByName(String name);
}
