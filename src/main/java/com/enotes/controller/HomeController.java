package com.enotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.enotes.service.HomeService;
import com.enotes.service.UserService;
import com.enotes.util.CommonUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/home")
public class HomeController {

	@Autowired
	private HomeService homeService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/verify")
	ResponseEntity<?> verify(@RequestParam(name = "uid") Integer id, @RequestParam String code) throws Exception{
		Boolean verifyAccount = homeService.verifyAccount(id, code);
		
		if(verifyAccount) {
			return CommonUtil.createBuildResponseMessage("Verification Successful", HttpStatus.OK);
		}
		return CommonUtil.createErrorResponseMessage("Invalid Verification Link", HttpStatus.BAD_REQUEST);
	}
	
	ResponseEntity<?> sendEmailForPasswordReset(@RequestParam String email, HttpServletRequest request){
		userService.sendEmailPasswordReset(email,request);
		return CommonUtil.createBuildResponseMessage("Email send successfully! check your email for password reset", HttpStatus.OK);
	}
}
