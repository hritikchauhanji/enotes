package com.enotes.service.impl;

import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.enotes.config.security.CustomUserDetails;
import com.enotes.dto.EmailRequest;
import com.enotes.dto.LoginRequest;
import com.enotes.dto.LoginResponse;
import com.enotes.dto.UserDto;
import com.enotes.entity.AccountStatus;
import com.enotes.entity.Role;
import com.enotes.entity.User;
import com.enotes.repository.RoleRepository;
import com.enotes.repository.UserRepository;
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

	@Override
	public Boolean registerUser(UserDto userDto,String url) throws Exception {
		// validation
		validation.userRegisterValidation(userDto);
		
		User user = mapper.map(userDto, User.class);
		setRole(userDto, user);
		AccountStatus status =  AccountStatus.builder()
				.isActive(false)
				.verificationCode(UUID.randomUUID().toString())
				.build();
		user.setStatus(status);
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

	private void setRole(UserDto userDto, User user) {
		List<Integer> reqRoleId = userDto.getRoles().stream().map(r->r.getId()).toList();
		List<Role> roles = roleRepository.findAllById(reqRoleId);
		user.setRoles(roles);
	}
	
	
	@Override
	public LoginResponse login(LoginRequest request) {
		Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		
		if(authenticate.isAuthenticated()) {
			CustomUserDetails customUserDetails = (CustomUserDetails)authenticate.getPrincipal();
			
			String token = "sjhdisadjaksdaidsi";
			
			LoginResponse response = LoginResponse.builder()
					.token(token)
					.user(mapper.map(customUserDetails.getUser(), UserDto.class))
					.build();
			
			return response;
		}
		
		return null;
	}

}
