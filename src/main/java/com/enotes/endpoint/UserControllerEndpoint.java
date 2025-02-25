package com.enotes.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.enotes.dto.PasswordChangeRequest;

@RequestMapping("api/v1/user")
public interface UserControllerEndpoint {
	
	@GetMapping("/profile")
	public ResponseEntity<?> getProfile();
	
	@PostMapping("/pass-chng")
	public ResponseEntity<?> passwordChange(@RequestBody PasswordChangeRequest passwordChangeRequest);
}
