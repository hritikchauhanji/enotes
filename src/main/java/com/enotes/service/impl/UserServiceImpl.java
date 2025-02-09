package com.enotes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.enotes.dto.PasswordChangeRequest;
import com.enotes.entity.User;
import com.enotes.repository.UserRepository;
import com.enotes.service.UserService;
import com.enotes.util.CommonUtil;

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

}
