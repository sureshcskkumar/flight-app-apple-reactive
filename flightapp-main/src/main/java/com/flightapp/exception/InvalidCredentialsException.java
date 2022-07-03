package com.flightapp.exception;

public class InvalidCredentialsException extends Exception {

	private static final long serialVersionUID = 8910754704103970029L;

	public InvalidCredentialsException() {
	}
	
	public InvalidCredentialsException(String message) {
		super(message);
	}
}
