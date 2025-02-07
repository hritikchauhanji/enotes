package com.enotes.service.impl;

import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.enotes.config.security.CustomUserDetails;
import com.enotes.dto.EmailRequest;
import com.enotes.dto.LoginRequest;
import com.enotes.dto.LoginResponse;
import com.enotes.dto.UserRequest;
import com.enotes.entity.AccountStatus;
import com.enotes.entity.Role;
import com.enotes.entity.User;
import com.enotes.repository.RoleRepository;
import com.enotes.repository.UserRepository;
import com.enotes.service.JwtService;
import com.enotes.service.UserService;
import com.enotes.util.Validation;

@Service
public class UserServiceImpl implements UserService {
	
	

	@Autowired
	private Validation validation;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private JwtService jwtService;

	@Override
	public Boolean registerUser(UserRequest userRequest,String url) throws Exception {
		// validation
		validation.userRegisterValidation(userRequest);
		
		User user = mapper.map(userRequest, User.class);
		setRole(userRequest, user);
		AccountStatus status =  AccountStatus.builder()
				.isActive(false)
				.verificationCode(UUID.randomUUID().toString())
				.build();
		user.setStatus(status);
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		if(!ObjectUtils.isEmpty(user)) {
			//send Email
			emailSend(user, url);
			
			return true;
		}
		return false;
	}

	private void emailSend(User user,String url) throws Exception {
		
		String message = "Hi,<b>"+user.getFirstName()+" "+user.getLastName()+"</b>"
				+"<br>Your account register successful."
				+"<br>Click the below link verify your account."
				+"<br><a href='[[url]]'>Click Me</a>"
				+"<br><br>Thanks,<br>Enotes.com";
		
		message= message.replace("[[url]]", url+"/api/v1/home/verify?uid="+user.getId()+"&&code="+user.getStatus().getVerificationCode());
		
		EmailRequest emailRequest = EmailRequest.builder()
				.to(user.getEmail())
				.title("Account Creating Confirmation")
				.subject("Account Created Success")
				.message(message)
				.build();
		emailService.sendEmail(emailRequest);
	}

	private void setRole(UserRequest userRequest, User user) {
		List<Integer> reqRoleId = userRequest.getRoles().stream().map(r->r.getId()).toList();
		List<Role> roles = roleRepository.findAllById(reqRoleId);
		user.setRoles(roles);
	}
	
	
	@Override
	public LoginResponse login(LoginRequest request) {
		Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		
		if(authenticate.isAuthenticated()) {
			CustomUserDetails customUserDetails = (CustomUserDetails)authenticate.getPrincipal();
			
			String token = jwtService.generateToken(customUserDetails.getUser());
			
			LoginResponse response = LoginResponse.builder()
					.token(token)
					.user(mapper.map(customUserDetails.getUser(), UserRequest.class))
					.build();
			
			return response;
		}
		
		return null;
	}

}
