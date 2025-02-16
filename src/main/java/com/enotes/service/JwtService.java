package com.enotes.service;

import org.springframework.security.core.userdetails.UserDetails;

import com.enotes.entity.User;

public interface JwtService {

	String generateToken(User user);
	
	String extractUsername(String token);
	
	Boolean validateToken(String token, UserDetails userDetails);
}
