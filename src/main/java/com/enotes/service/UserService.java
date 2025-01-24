package com.enotes.service;

import com.enotes.dto.UserDto;

public interface UserService {

	Boolean registerUser(UserDto userDto) throws Exception;
}
