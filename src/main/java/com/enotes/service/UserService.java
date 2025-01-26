package com.enotes.service;

import com.enotes.dto.UserDto;

public interface UserService {

	Boolean registerUser(UserDto userDto, String url) throws Exception;
}
