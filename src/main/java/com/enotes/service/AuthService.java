package com.enotes.service;

import com.enotes.dto.LoginRequest;
import com.enotes.dto.LoginResponse;
import com.enotes.dto.UserRequest;

public interface AuthService {

	Boolean registerUser(UserRequest userRequest, String url) throws Exception;

	LoginResponse login(LoginRequest request);
}
