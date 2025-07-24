package com.enotes.controller;

import com.enotes.dto.UserRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RestController;

import com.enotes.dto.PasswordChangeRequest;
import com.enotes.dto.UserResponse;
import com.enotes.endpoint.UserControllerEndpoint;
import com.enotes.entity.User;
import com.enotes.service.UserService;
import com.enotes.util.CommonUtil;

@RestController
public class UserController implements UserControllerEndpoint {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ModelMapper mapper;

	@Override
	public ResponseEntity<?> getProfile(){
		User loggedInUser = CommonUtil.getLoggedInUser();
		UserResponse userResponse = mapper.map(loggedInUser, UserResponse.class);
		return CommonUtil.createBuildResponse(userResponse, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> passwordChange(PasswordChangeRequest passwordChangeRequest){
		userService.passwordChange(passwordChangeRequest);
		return CommonUtil.createBuildResponseMessage("Password change successfully...", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> updateProfile(UserRequest userRequest) {
		UserResponse updateUser = userService.updateUserProfile(userRequest);
		if(!ObjectUtils.isEmpty(updateUser)) {
			return CommonUtil.createBuildResponse(updateUser, HttpStatus.OK);
		}
		return CommonUtil.createErrorResponseMessage("Profile not update", HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
