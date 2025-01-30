package com.enotes.service;

import com.enotes.entity.User;

public interface JwtService {

	String generateToken(User user);
}
