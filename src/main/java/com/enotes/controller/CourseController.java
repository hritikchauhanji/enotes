package com.enotes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RestController;

import com.enotes.dto.CourseRequest;
import com.enotes.dto.CourseResponse;
import com.enotes.endpoint.CourseControllerEndpoint;
import com.enotes.service.CourseService;
import com.enotes.util.CommonUtil;


@RestController
public class CourseController implements CourseControllerEndpoint {

	@Autowired
	private CourseService courseService;

	@Override
	public ResponseEntity<?> saveCourse(CourseRequest courseRequest) {
		Boolean saveCategory = courseService.saveCourse(courseRequest);
		if (saveCategory) {
			return CommonUtil.createBuildResponseMessage("Category saved", HttpStatus.CREATED);
//			return new ResponseEntity<>("saved", HttpStatus.CREATED);
		} else {
//			return new ResponseEntity<>("not saved", HttpStatus.INTERNAL_SERVER_ERROR);
			return CommonUtil.createErrorResponseMessage("Category not saved", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<?> getAllCourses() {
		List<CourseResponse> getAll = courseService.getAllCourse();
		if (CollectionUtils.isEmpty(getAll)) {
			return ResponseEntity.noContent().build();
		} else {
			return CommonUtil.createBuildResponse(getAll, HttpStatus.OK);
//			return new ResponseEntity<>(getAll, HttpStatus.OK);
		}
	}

	@Override
	public ResponseEntity<?> getActiveCourses() {
		List<CourseResponse> getAll = courseService.getAllActiveCourse();
		if (CollectionUtils.isEmpty(getAll)) {
			return ResponseEntity.noContent().build();
		} else {
//			return new ResponseEntity<>(getAll, HttpStatus.OK);
			return CommonUtil.createBuildResponse(getAll, HttpStatus.OK);
		}
	}

	@Override
	public ResponseEntity<?> getCourseById(Integer id) throws Exception {
		CourseResponse courseRequest = courseService.getCourseById(id);
		if (ObjectUtils.isEmpty(courseRequest)) {
//			return new ResponseEntity<>("Interval Server Error", HttpStatus.NOT_FOUND);
			return CommonUtil.createErrorResponseMessage("Internal server error", HttpStatus.NOT_FOUND);
		} else {
//			return new ResponseEntity<>(categoryDto, HttpStatus.OK);
			return CommonUtil.createBuildResponse(courseRequest, HttpStatus.OK);
		}
//		try {
//			CategoryDto categoryDto = services.getCategoryById(id);
//			if (ObjectUtils.isEmpty(categoryDto)) {
//				return new ResponseEntity<>("Category not found by Id = " + id, HttpStatus.NOT_FOUND);
//			} else {
//				return new ResponseEntity<>(categoryDto, HttpStatus.OK);
//			}
//		} catch (ResourceNotFoundException e) {
//			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//		} catch (Exception e) {
//			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//		}

	}

	@Override
	public ResponseEntity<?> deleteCourseById(Integer id) {
		Boolean deleted = courseService.DeleteCourseById(id);
		if (deleted) {
//			return new ResponseEntity<>("Category delete successfully", HttpStatus.OK);
			return CommonUtil.createBuildResponseMessage("Category deleted successfully", HttpStatus.OK);
		} else {
//			return new ResponseEntity<>("Category not deleted", HttpStatus.INTERNAL_SERVER_ERROR);
			return CommonUtil.createErrorResponseMessage("Category not deleted", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
