package com.enotes.service.impl;

import com.enotes.service.HomeService;
import com.enotes.entity.AccountStatus;
import com.enotes.entity.User;
import com.enotes.exceptionhandling.ExistDataException;
import com.enotes.exceptionhandling.ResourceNotFoundException;
import com.enotes.exceptionhandling.SuccessException;
import com.enotes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HomeServiceImpl implements HomeService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public Boolean verifyAccount(Integer id, String code) throws Exception {
		User user = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Invalid Id..."));
		
		if(user.getStatus().getVerificationCode()==null) {
			throw new SuccessException("Verification is already done...");
		}
		
		if(user.getStatus().getVerificationCode().equals(code)) {
			
			AccountStatus status = user.getStatus();
			status.setIsActive(true);
			status.setVerificationCode(null);
			
			userRepository.save(user);
			
			return true;
		}
		
		return false;
	}

}
