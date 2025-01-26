package com.enotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enotes.dto.UserDto;
import com.enotes.service.UserService;
import com.enotes.util.CommonUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/auth")
public class UserController {
	
	@Autowired
	private UserService userService;

	@PostMapping
	ResponseEntity<?> register(@RequestBody UserDto userDto, HttpServletRequest request) throws Exception{
		String url = CommonUtil.getUrl(request);
		Boolean registerUser = userService.registerUser(userDto,url);
		if(registerUser) {
			return CommonUtil.createBuildResponseMessage("Registration Successful...", HttpStatus.CREATED);
		}
		return CommonUtil.createErrorResponseMessage("Regitration Failed", HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
