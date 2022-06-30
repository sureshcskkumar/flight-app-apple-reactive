package com.flightapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flightapp.entity.Ticket;
import com.flightapp.model.BookingDetails;
import com.flightapp.service.BookingService;

@RestController
@CrossOrigin
@RequestMapping("/flight")
public class BookingController {

	@Autowired
	private BookingService bookingService;
	
	@PostMapping("/booking/{scheduleId}")
	public ResponseEntity<List<Ticket>> bookFlight(@PathVariable long scheduleId, @RequestBody BookingDetails bookingDetails) {
		
		String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
		
		return bookingService.bookFlight(userEmail, scheduleId, bookingDetails);
	}
	
	@GetMapping("/booking/history")
	public ResponseEntity<List<Ticket>> getBookingHistory() {
		
		String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
		
		return bookingService.getBookingHistory(userEmail);
	}
}
