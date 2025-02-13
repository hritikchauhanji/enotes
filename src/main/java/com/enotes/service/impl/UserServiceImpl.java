package com.enotes.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.enotes.dto.EmailRequest;
import com.enotes.dto.PasswordChangeRequest;
import com.enotes.dto.PswdResetRequest;
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
	
	@Autowired
	private EmailService emailService;

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
	public void sendEmailPasswordReset(String email, String url) throws Exception {
		User user = userRepository.findByEmail(email);
		if(ObjectUtils.isEmpty(user)) {
			throw new ResourceNotFoundException("Invalid Email...");
		}
		String resetToken = UUID.randomUUID().toString();
		user.getStatus().setPasswordResetToken(resetToken);
		User updateUser = userRepository.save(user);
		sendEmailRequest(user, url);
	}

	private void sendEmailRequest(User user, String url) throws Exception {
		String message = "Hi,<b>"+user.getFirstName()+" "+user.getLastName()+"</b>"
				+"<br>You are requested to reset password."
				+"<br>Click the below link to change your password."
				+"<br><a href='[[url]]'>Change My Password</a>"
				+"<br>Ignore this message if you don't change your password..."
				+"<br><br>Thanks,<br>Enotes.com";
		
		message= message.replace("[[url]]", url+"/api/v1/home/verify-password-link?uid="+user.getId()+"&&code="+user.getStatus().getPasswordResetToken());
		
		EmailRequest emailRequest = EmailRequest.builder()
				.to(user.getEmail())
				.title("Password Reset")
				.subject("Password reset link")
				.message(message)
				.build();
		emailService.sendEmail(emailRequest);
	}

	@Override
	public void verifyPswdResetLink(Integer uid, String code) throws Exception {
		User user = userRepository.findById(uid).orElseThrow(() -> new ResourceNotFoundException("Invalid user"));
		verifyPswdResetToken(user.getStatus().getPasswordResetToken(),code);
	}

	private void verifyPswdResetToken(String existToken, String reqToken) {
		if(StringUtils.hasText(reqToken)) {
			if(!StringUtils.hasText(existToken)) {
				throw new IllegalArgumentException("Already Password Reset...");
			}
			if(!existToken.equals(reqToken)) {
				throw new IllegalArgumentException("Invalid url...");
			}
		} else {
			throw new IllegalArgumentException("Invalid token...");
		}
	}

	@Override
	public void resetPswd(PswdResetRequest pswdResetRequest) throws Exception {
		User user = userRepository.findById(pswdResetRequest.getUid()).orElseThrow(() -> new ResourceNotFoundException("Invalid user"));
		String encodePassword = encoder.encode(pswdResetRequest.getNewPassword());
		user.setPassword(encodePassword);
		user.getStatus().setPasswordResetToken(null);
		userRepository.save(user);
	}

}
