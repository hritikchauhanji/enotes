package com.enotes.exceptionhandling;

public class JwtTokenExpiredException extends RuntimeException {

	public JwtTokenExpiredException(String message) {
		super(message);
	}

}
