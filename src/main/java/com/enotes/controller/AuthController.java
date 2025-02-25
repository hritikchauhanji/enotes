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
import com.enotes.endpoint.AuthControllerEndpoint;
import com.enotes.service.AuthService;
import com.enotes.util.CommonUtil;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class AuthController implements AuthControllerEndpoint {
	
	@Autowired
	private AuthService authService;

	@Override
	public ResponseEntity<?> register(@RequestBody UserRequest userRequest, HttpServletRequest request) throws Exception{
		log.info("AuthController : register() : Exceution Start");
		String url = CommonUtil.getUrl(request);
		Boolean registerUser = authService.registerUser(userRequest,url);
		if(!registerUser) {
			log.info("Error: {}", "Register failed");
			return CommonUtil.createErrorResponseMessage("Regitration Failed", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		log.info("AuthController : register() : Exceution Start");
		return CommonUtil.createBuildResponseMessage("Registration Successful...", HttpStatus.CREATED);
		
	}
	
	@Override
	public ResponseEntity<?> login(@RequestBody LoginRequest request) throws Exception{
		LoginResponse response = authService.login(request);
		
		if(ObjectUtils.isEmpty(response)) {
			return CommonUtil.createErrorResponseMessage("Invalid Credential", HttpStatus.BAD_REQUEST);
		}
		return CommonUtil.createBuildResponse(response, HttpStatus.OK);
	}
}
