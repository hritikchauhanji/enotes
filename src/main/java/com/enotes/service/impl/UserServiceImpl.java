package com.enotes.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.enotes.dto.PasswordChangeRequest;
import com.enotes.entity.User;
import com.enotes.exceptionhandling.ResourceNotFoundException;
import com.enotes.repository.UserRepository;
import com.enotes.service.UserService;
import com.enotes.util.CommonUtil;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder encoder;

	@Override
	public void passwordChange(PasswordChangeRequest passwordChangeRequest) {
		User loggedInUser = CommonUtil.getLoggedInUser();
		
		if(!encoder.matches(passwordChangeRequest.getOldPassword(), loggedInUser.getPassword())){
			throw new IllegalArgumentException("Old password is wrong...");
		}
		String newPassword = encoder.encode(passwordChangeRequest.getNewPassword());
		loggedInUser.setPassword(newPassword);
		userRepository.save(loggedInUser);
	}

	@Override
	public void sendEmailPasswordReset(String email, HttpServletRequest request) throws Exception {
		User user = userRepository.findByEmail(email);
		if(ObjectUtils.isEmpty(user)) {
			throw new ResourceNotFoundException("Invalid Email...");
		}
		String resetToken = UUID.randomUUID().toString();
		user.getStatus().setPasswordResetToken(resetToken);
		User updateUser = userRepository.save(user);
		
		String url = CommonUtil.getUrl(request);
		
	}

}
