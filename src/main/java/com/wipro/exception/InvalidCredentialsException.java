package com.wipro.exception;

import org.springframework.security.authentication.BadCredentialsException;

public class InvalidCredentialsException extends BadCredentialsException {
	public InvalidCredentialsException() {
		super("Invalid Credentials Exception");
	}
	public InvalidCredentialsException(String message) {
		super(message);
	}
	public InvalidCredentialsException(String message, Throwable t) {
		super(message, t);
	}
}