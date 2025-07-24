package com.enotes.service.impl;

import java.util.UUID;

import com.enotes.dto.*;
import com.enotes.entity.Course;
import com.enotes.entity.Semester;
import com.enotes.repository.CourseRepository;
import com.enotes.repository.SemesterRepository;
import com.enotes.service.JwtService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.enotes.entity.User;
import com.enotes.exceptionhandling.ResourceNotFoundException;
import com.enotes.repository.UserRepository;
import com.enotes.service.UserService;
import com.enotes.util.CommonUtil;

@Service
public class UserServiceImpl implements UserService {

	@Value("${frontend.url}")
	private String frontendUrl;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private EmailService emailService;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private SemesterRepository semesterRepository;

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

		message= message.replace("[[url]]", frontendUrl+"/auth/reset-password?uid="+user.getId()+"&&code="+user.getStatus().getPasswordResetToken());

		EmailRequest emailRequest = EmailRequest.builder()
				.to(user.getEmail())
				.title("Password Reset")
				.subject("Password reset link")
				.message(message)
				.build();
		emailService.sendEmail(emailRequest);
	}



	@Override
	public void resetPswd(PswdResetRequest pswdResetRequest) throws Exception {
		User user = userRepository.findById(pswdResetRequest.getUid())
				.orElseThrow(() -> new ResourceNotFoundException("Invalid user"));

		// Move token verification here
		verifyPswdResetToken(user.getStatus().getPasswordResetToken(), pswdResetRequest.getToken());

		// Save new password
		String encodePassword = encoder.encode(pswdResetRequest.getNewPassword());
		user.setPassword(encodePassword);
		user.getStatus().setPasswordResetToken(null); // clear token after reset
		userRepository.save(user);
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
	public UserResponse updateUserProfile(UserRequest userRequest) {
		User user = mapper.map(userRequest, User.class);
		User existUser = userRepository.findByEmail(user.getEmail());
		if(!ObjectUtils.isEmpty(existUser)) {
			existUser.setFirstName(user.getFirstName());
			existUser.setLastName(user.getLastName());
			//Load full Course and Semester entities
			Course course = courseRepository.findById(userRequest.getCourseId())
					.orElse(null);
			Semester semester = semesterRepository.findById(userRequest.getSemesterId())
					.orElse(null);
			existUser.setCourse(course);
			existUser.setSemester(semester);
			existUser.setMobNo(user.getMobNo());
			userRepository.save(existUser);
			// Generate updated token
			String token = jwtService.generateToken(existUser);

			// Map back to UserResponse
			UserResponse response = mapper.map(existUser, UserResponse.class);
			response.setToken(token); //Set the token

			return response;

		}
		return null;
	}

}
