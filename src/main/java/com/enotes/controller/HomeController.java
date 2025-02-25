package com.enotes.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.enotes.dto.PswdResetRequest;
import com.enotes.endpoint.HomeControllerEndpoint;
import com.enotes.service.HomeService;
import com.enotes.service.UserService;
import com.enotes.util.CommonUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class HomeController implements HomeControllerEndpoint{
	
	Logger log =LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private HomeService homeService;
	
	@Autowired
	private UserService userService;
	
	@Override
	public ResponseEntity<?> verify(@RequestParam(name = "uid") Integer id, @RequestParam String code) throws Exception{
		log.info("HomeController : verifyUserAccount() : Exceution Start");
		Boolean verifyAccount = homeService.verifyAccount(id, code);
		
		if(verifyAccount) {
			return CommonUtil.createBuildResponseMessage("Verification Successful", HttpStatus.OK);
		}
		log.info("HomeController : verifyUserAccount() : Exceution End");
		return CommonUtil.createErrorResponseMessage("Invalid Verification Link", HttpStatus.BAD_REQUEST);
	}
	
	@Override
	public ResponseEntity<?> sendEmailForPasswordReset(@RequestParam String email, HttpServletRequest request) throws Exception{
		String url = CommonUtil.getUrl(request);
		userService.sendEmailPasswordReset(email,url);
		return CommonUtil.createBuildResponseMessage("Email send successfully! check your email for password reset", HttpStatus.OK);  
	}
	
	@Override
	public ResponseEntity<?> verifyPasswordResetLink(@RequestParam Integer uid,@RequestParam String code) throws Exception{
		userService.verifyPswdResetLink(uid,code);
		return CommonUtil.createBuildResponseMessage("Verification Successful...", HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> resetPassword(@RequestBody PswdResetRequest pswdResetRequest) throws Exception{
		userService.resetPswd(pswdResetRequest);
		return CommonUtil.createBuildResponseMessage("Password reset successfully...", HttpStatus.OK);
	}
}
