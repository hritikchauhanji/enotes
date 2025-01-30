package com.enotes.service;

import com.enotes.dto.LoginRequest;
import com.enotes.dto.LoginResponse;
import com.enotes.dto.UserDto;

public interface UserService {

	Boolean registerUser(UserDto userDto, String url) throws Exception;

	LoginResponse login(LoginRequest request);
}
