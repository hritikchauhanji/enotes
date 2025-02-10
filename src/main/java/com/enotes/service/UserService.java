package com.enotes.service;

import com.enotes.dto.PasswordChangeRequest;

import jakarta.servlet.http.HttpServletRequest;

public interface UserService{
 
	void passwordChange(PasswordChangeRequest passwordChangeRequest);

	void sendEmailPasswordReset(String email, HttpServletRequest request) throws Exception;
}
