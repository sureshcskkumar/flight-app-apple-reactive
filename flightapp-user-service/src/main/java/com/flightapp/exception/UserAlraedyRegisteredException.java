package com.flightapp.exception;

public class UserAlraedyRegisteredException extends Exception{

	private static final long serialVersionUID = -2465437793830559639L;

	public UserAlraedyRegisteredException() {
	}
	
	public UserAlraedyRegisteredException(String message) {
		super(message);
	}
}
