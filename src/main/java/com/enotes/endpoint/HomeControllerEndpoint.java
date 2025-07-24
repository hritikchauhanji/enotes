package com.enotes.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.enotes.dto.PswdResetRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@Tag(name = "Home", description = "All The Home APIs")
@RequestMapping("/api/v1/home")
public interface HomeControllerEndpoint {

	@Operation(summary = "Verification User Account", tags = {"Home"}, description = "User account verification after register")
	@GetMapping("/verify")
	public ResponseEntity<?> verify(@RequestParam(name = "uid") Integer id, @RequestParam String code) throws Exception;
	
	@Operation(summary = "Send Email For Password Reset", tags = {"Home"}, description = "User can send email for password reset")
	@GetMapping("/send-email-reset")
	public ResponseEntity<?> sendEmailForPasswordReset(@RequestParam String email, HttpServletRequest request) throws Exception;
	
	@Operation(summary = "Reset Password", tags = {"Home"}, description = "User can changes password reset")
	@PostMapping("/reset-pswd")
	public ResponseEntity<?> resetPassword(@RequestBody PswdResetRequest pswdResetRequest) throws Exception;

}
