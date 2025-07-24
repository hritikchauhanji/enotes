package com.enotes.service;

import com.enotes.dto.PasswordChangeRequest;
import com.enotes.dto.PswdResetRequest;
import com.enotes.dto.UserRequest;
import com.enotes.dto.UserResponse;


public interface UserService{
 
	void passwordChange(PasswordChangeRequest passwordChangeRequest);

	void sendEmailPasswordReset(String email, String url) throws Exception;

	void resetPswd(PswdResetRequest pswdResetRequest) throws Exception;

	UserResponse updateUserProfile(UserRequest user);
}
