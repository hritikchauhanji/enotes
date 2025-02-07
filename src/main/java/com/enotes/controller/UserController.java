package com.enotes.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enotes.dto.UserResponse;
import com.enotes.entity.User;
import com.enotes.util.CommonUtil;

@RestController
@RequestMapping("api/v1/user")
public class UserController {
	
	@Autowired
	private ModelMapper mapper;

	@GetMapping("/profile")
	public ResponseEntity<?> getProfile(){
		User loggedInUser = CommonUtil.getLoggedInUser();
		UserResponse userResponse = mapper.map(loggedInUser, UserResponse.class);
		return CommonUtil.createBuildResponse(userResponse, HttpStatus.OK);
	}
}
