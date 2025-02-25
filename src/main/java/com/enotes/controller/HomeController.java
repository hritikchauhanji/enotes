package com.enotes.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.enotes.dto.PswdResetRequest;
import com.enotes.service.HomeService;
import com.enotes.service.UserService;
import com.enotes.util.CommonUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/home")
public class HomeController {
	
	Logger log =LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private HomeService homeService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/verify")
	ResponseEntity<?> verify(@RequestParam(name = "uid") Integer id, @RequestParam String code) throws Exception{
		log.info("HomeController : verifyUserAccount() : Exceution Start");
		Boolean verifyAccount = homeService.verifyAccount(id, code);
		
		if(verifyAccount) {
			return CommonUtil.createBuildResponseMessage("Verification Successful", HttpStatus.OK);
		}
		log.info("HomeController : verifyUserAccount() : Exceution End");
		return CommonUtil.createErrorResponseMessage("Invalid Verification Link", HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/send-email-reset")
	ResponseEntity<?> sendEmailForPasswordReset(@RequestParam String email, HttpServletRequest request) throws Exception{
		String url = CommonUtil.getUrl(request);
		userService.sendEmailPasswordReset(email,url);
		return CommonUtil.createBuildResponseMessage("Email send successfully! check your email for password reset", HttpStatus.OK);  
	}
	
	@GetMapping("/verify-password-link")
	ResponseEntity<?> verifyPasswordResetLink(@RequestParam Integer uid,@RequestParam String code) throws Exception{
		userService.verifyPswdResetLink(uid,code);
		return CommonUtil.createBuildResponseMessage("Verification Successful...", HttpStatus.OK);
	}
	
	@PostMapping("/reset-pswd")
	ResponseEntity<?> resetPassword(@RequestBody PswdResetRequest pswdResetRequest) throws Exception{
		userService.resetPswd(pswdResetRequest);
		return CommonUtil.createBuildResponseMessage("Password reset successfully...", HttpStatus.OK);
	}
}
