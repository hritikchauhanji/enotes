package com.enotes.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.enotes.dto.CourseRequest;
import com.enotes.dto.CourseResponse;
import com.enotes.entity.Course;
import com.enotes.exceptionhandling.ExistDataException;
import com.enotes.exceptionhandling.ResourceNotFoundException;
import com.enotes.repository.CourseRepository;
import com.enotes.service.CacheManagerService;
import com.enotes.service.CourseService;
import com.enotes.util.Validation;

@Service
public class CourseServiceImpl implements CourseService {
	
	@Autowired
	private CourseRepository courseRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private Validation validation;
	
	@Autowired
	private CacheManagerService cacheService;

	@Override
	public Boolean saveCourse(CourseRequest courseRequest) {
//		Category category = new Category();
//		category.setName(categoryDto.getName());
//		category.setDescription(categoryDto.getDescription());
//		category.setIsActive(categoryDto.getIsActive());
//		category.setCreatedBy(categoryDto.getCreatedBy());
//		category.setUpdatedBy(categoryDto.getUpdatedBy()); 
		
		//validation checking :-
		validation.courseValidation(courseRequest);
		
		// check category exist or not :-
		Boolean existsByName = courseRepository.existsByName(courseRequest.getName().trim());
		
		if(existsByName) {
			throw new ExistDataException("Category is already exists...");
		}
		
		Course course = mapper.map(courseRequest, Course.class);
		if(ObjectUtils.isEmpty(course.getId())) {
			course.setIsDeleted(false);
//			category.setCreatedBy(1);
//		category.setCreatedOn(new Date());
//		category.setUpdatedOn(new Date());
		} else {
			updateCourse(course);
		}
		
		Course save = courseRepository.save(course);
		if(ObjectUtils.isEmpty(save)) {
			return false;
		}
		return true;
	}

	private void updateCourse(Course course) {
		Optional<Course> findById = courseRepository.findById(course.getId());
		if(findById.isPresent()) {
			Course existCourse = findById.get();
			course.setCreatedBy(existCourse.getCreatedBy());
			course.setCreatedOn(existCourse.getCreatedOn());
			course.setIsDeleted(existCourse.getIsDeleted());
//			category.setUpdatedBy(1);
//			category.setUpdatedOn(new Date());
		}
	}

	@Override
	@Cacheable("allCategory")
	public List<CourseResponse> getAllCourse() {
		List<Course> categories = courseRepository.findByIsDeletedFalse();
		List<CourseResponse> categoriesDto = categories.stream().map(cat -> mapper.map(cat, CourseResponse.class)).toList();
		return categoriesDto;
	}

	@Override
	@Cacheable("activeCategory")
	public List<CourseResponse> getAllActiveCourse() {
		List<Course> categories = courseRepository.findAllByIsActiveTrue();
		List<CourseResponse> categoriesResponse = categories.stream().map(cat -> mapper.map(cat, CourseResponse.class)).toList();
		return categoriesResponse;
	}

	@Override
	@Cacheable(value = "getCategoryById", key = "#id")
	public CourseResponse getCourseById(Integer id) throws Exception {
		Course course = courseRepository.findByIdAndIsDeletedFalse(id).orElseThrow(() -> new ResourceNotFoundException("Category not found by id = " + id));
		if(!ObjectUtils.isEmpty(course)) {
//			if(category.getName() == null) {
//				throw new IllegalArgumentException("name is null");
//			}
			
//			category.getName().toUpperCase();
				return mapper.map(course, CourseResponse.class);
		}

		return null;
	}

	@Override
	@CacheEvict(value = "getCategoryById", key = "#id")
	public Boolean DeleteCourseById(Integer id) {
		Optional<Course> findByCategory = courseRepository.findById(id);
		if(findByCategory.isPresent()) {
			Course course = findByCategory.get();
			course.setIsDeleted(true);
			courseRepository.save(course);
			
			//remove from cache
			cacheService.removeCacheById(Arrays.asList("allCategory","activeCategory"));
			
			return true;
		}
		return false;
	}

}
