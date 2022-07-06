package com.flightapp.exception;

public class InvalidSearchException extends Exception {

	private static final long serialVersionUID = -3835750693479489975L;
	
	public InvalidSearchException() {
		super();
	}
	
	public InvalidSearchException(String message) {
		super(message);
	}

	public InvalidSearchException(Throwable ex) {
		super(ex);
	}
	
	public InvalidSearchException(String message, Throwable ex) {
		super(message, ex);
	}
}
