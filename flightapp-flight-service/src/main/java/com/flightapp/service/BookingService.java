package com.flightapp.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.flightapp.entity.Ticket;
import com.flightapp.model.BookingDetails;

public interface BookingService {

	ResponseEntity<List<Ticket>> bookFlight(String userEmail, long scheduleId, BookingDetails bookingDetails);

	ResponseEntity<List<Ticket>> getBookingHistory(String userEmail);

}
