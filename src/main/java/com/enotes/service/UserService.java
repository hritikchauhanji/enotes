package com.enotes.service;

import com.enotes.dto.PasswordChangeRequest;
import com.enotes.dto.PswdResetRequest;

import jakarta.servlet.http.HttpServletRequest;

public interface UserService{
 
	void passwordChange(PasswordChangeRequest passwordChangeRequest);

	void sendEmailPasswordReset(String email, String url) throws Exception;

	void verifyPswdResetLink(Integer uid, String code) throws Exception;

	void resetPswd(PswdResetRequest pswdResetRequest) throws Exception;
}
