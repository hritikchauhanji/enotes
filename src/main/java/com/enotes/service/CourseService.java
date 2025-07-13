package com.enotes.service;

import java.util.List;

import com.enotes.dto.CourseRequest;
import com.enotes.dto.CourseResponse;
public interface CourseService {

	Boolean saveCourse(CourseRequest courseRequest);
	
	List<CourseResponse> getAllCourse();

	List<CourseResponse> getAllActiveCourse();

	CourseResponse getCourseById(Integer id) throws Exception;

	Boolean DeleteCourseById(Integer id);
}