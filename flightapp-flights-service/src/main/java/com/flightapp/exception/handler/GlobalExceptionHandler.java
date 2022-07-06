package com.flightapp.exception.handler;

import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.flightapp.exception.InvalidSearchException;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import reactor.core.publisher.Mono;

@Getter
@Setter
@AllArgsConstructor
class ErrorResponse{
	private String message;
	private LocalDateTime now;

}

@ControllerAdvice
public class GlobalExceptionHandler {

	
	@ExceptionHandler(InvalidSearchException.class)
	public Mono<ErrorResponse> handle(InvalidSearchException e) {
		e.printStackTrace();
		ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now());
		return Mono.just(errorResponse);
	}
	
	
	@ExceptionHandler(Exception.class)
	public Mono<ErrorResponse> handle(Exception e) {
		e.printStackTrace();
		ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now());
		return Mono.just(errorResponse);
	}
}
