package com.enotes.exceptionhandling;

import java.io.FileNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.enotes.util.CommonUtil;

import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleException(Exception e) {
		log.error("GlobalExceptionHandler :: handleException :: " , e.getMessage());
//		return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		return CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(SuccessException.class)
	public ResponseEntity<?> handleSuccessException(SuccessException e) {
		log.error("GlobalExceptionHandler :: handleException :: " , e.getMessage());
//		return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		return CommonUtil.createBuildResponseMessage(e.getMessage(), HttpStatus.OK);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException e) {
		log.error("GlobalExceptionHandler :: handleException :: " , e.getMessage());
//		return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		return CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<?> handleNullPointerException(Exception e) {
		log.error("GlobalExceptionHandler :: handleNullPointerException :: " , e.getMessage());
//		return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		return CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceNotFoundException(Exception e) {
		log.error("GlobalExceptionHandler :: handleResourceNotFoundException :: " , e.getMessage());
//		return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
		return CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<?> handleValidationException(ValidationException e) {
//		return new ResponseEntity<>(e.getErrors(),HttpStatus.NOT_FOUND);
		return CommonUtil.createErrorResponse(e.getErrors(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ExistDataException.class)
	public ResponseEntity<?> handleExistDataException(ExistDataException e) {
//		return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
		return CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(FileNotFoundException.class)
	public ResponseEntity<?> handleFileNotFoundException(FileNotFoundException e) {
//		return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
		return CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException e) {
//		return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
		return CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.FORBIDDEN);
	}
	
}
