package com.enotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enotes.dto.LoginRequest;
import com.enotes.dto.LoginResponse;
import com.enotes.dto.UserRequest;
import com.enotes.service.UserService;
import com.enotes.util.CommonUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	
	@Autowired
	private UserService userService;

	@PostMapping
	ResponseEntity<?> register(@RequestBody UserRequest userRequest, HttpServletRequest request) throws Exception{
		String url = CommonUtil.getUrl(request);
		Boolean registerUser = userService.registerUser(userRequest,url);
		if(registerUser) {
			return CommonUtil.createBuildResponseMessage("Registration Successful...", HttpStatus.CREATED);
		}
		return CommonUtil.createErrorResponseMessage("Regitration Failed", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping("/login")
	ResponseEntity<?> login(@RequestBody LoginRequest request) throws Exception{
		LoginResponse response = userService.login(request);
		
		if(ObjectUtils.isEmpty(response)) {
			return CommonUtil.createErrorResponseMessage("Invalid Credential", HttpStatus.BAD_REQUEST);
		}
		return CommonUtil.createBuildResponse(response, HttpStatus.OK);
	}
}
