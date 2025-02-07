package com.enotes.util;

import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;

import com.enotes.config.security.CustomUserDetails;
import com.enotes.entity.User;
import com.enotes.handler.GenericResponse;

import jakarta.servlet.http.HttpServletRequest;

public class CommonUtil {

	public static ResponseEntity<?> createBuildResponse(Object data, HttpStatus status) {
		GenericResponse response = GenericResponse.builder().statusResponse(status).status("success").message("success")
				.data(data).build();
		return response.create();
	}

	public static ResponseEntity<?> createBuildResponseMessage(String message, HttpStatus status) {
		GenericResponse response = GenericResponse.builder().statusResponse(status).status("success").message(message)
				.build();
		return response.create();
	}

	public static ResponseEntity<?> createErrorResponse(Object data, HttpStatus status) {
		GenericResponse response = GenericResponse.builder().statusResponse(status).status("failed").message("failed")
				.data(data).build();
		return response.create();
	}

	public static ResponseEntity<?> createErrorResponseMessage(String message, HttpStatus status) {
		GenericResponse response = GenericResponse.builder().statusResponse(status).status("failed").message(message)
				.build();
		return response.create();
	}

	public static String getContentType(String originalFileName) {
		String extension = FilenameUtils.getExtension(originalFileName);
		switch (extension) {
		case "pdf":
			return "application/pdf";
		case "xlsx":
			return "application/vnd.openxmlformats-officedocument.spreadsheettml.sheet";
		case "txt":
			return "text/plan";
		case "png":
			return "image/png";
		case "jpeg":
			return "image/jpeg";
		default:
			return "application/octet-stream";
		}
	}

	public static String getUrl(HttpServletRequest request) {
		String apiUrl = request.getRequestURL().toString();
		apiUrl = apiUrl.replace(request.getServletPath(), "");
		return apiUrl;
	}
	
	public static User getLoggedInUser() {
		CustomUserDetails logUser = (CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return logUser.getUser();
	}
}
