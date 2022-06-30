package com.flightapp.exception.handler;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.flightapp.exception.InvalidCredentialsException;
import com.flightapp.exception.UserAlraedyRegisteredException;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
class ErrorResponse{
	private String message;
	private LocalDateTime now;

}

@ControllerAdvice
public class GloableExceptionHandler {

	@ExceptionHandler(InvalidCredentialsException.class)
	public ResponseEntity<ErrorResponse> handle(InvalidCredentialsException e) {
		e.printStackTrace();
		ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now());
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(UserAlraedyRegisteredException.class)
	public ResponseEntity<ErrorResponse> handle(UserAlraedyRegisteredException e) {
		e.printStackTrace();
		ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now());
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handle(Exception e) {
		e.printStackTrace();
		ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now());
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
	}
}
