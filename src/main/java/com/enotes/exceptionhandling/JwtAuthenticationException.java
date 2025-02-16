package com.enotes.exceptionhandling;

public class JwtAuthenticationException extends RuntimeException {

	public JwtAuthenticationException(String message) {
		super(message);
	}


}
