package com.flightapp.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.flightapp.entity.Schedule;
import com.flightapp.entity.Ticket;
import com.flightapp.model.AgeGroup;
import com.flightapp.model.BookingDetails;
import com.flightapp.model.Passenger;
import com.flightapp.repository.AirlineRepository;
import com.flightapp.repository.ScheduleRepository;
import com.flightapp.repository.TicketRepository;

@Service
public class BookingServiceImpl implements BookingService {

	@Autowired
	private AirlineRepository airlineRepository;
	
	@Autowired
	private ScheduleRepository scheduleRepository;
	
	@Autowired
	private TicketRepository ticketRepository;
	
	@Override
	public ResponseEntity<List<Ticket>> bookFlight(String userEmail, long scheduleId, BookingDetails bookingDetails) {

		List<Ticket> tickets = new ArrayList<>();
		Optional<Schedule> scheduleOptional = scheduleRepository.findById(scheduleId);
		Schedule schedule = scheduleOptional.get();
		String airlineName = schedule.getAirline().getName();
		// String pnrString = userEmail + LocalDateTime.now();
		// UUID pnr = UUID.nameUUIDFromBytes(pnrString.getBytes());
		
		int adultAndChildPassengerCount = 0;
		
		for (Passenger passenger: bookingDetails.getPassengers()) {
			
			if (passenger.getAgeGroup() == AgeGroup.ADULT || passenger.getAgeGroup() == AgeGroup.CHILD) {
				if (++adultAndChildPassengerCount > schedule.getNumberOfVacantSeats()) {
					return new ResponseEntity("There isn't enough Vacant Seats!", HttpStatus.BAD_REQUEST);
				}
			}
			
			String pnrString = userEmail + passenger.getName() + LocalDateTime.now();
			UUID pnr = UUID.nameUUIDFromBytes(pnrString.getBytes());
			
			Ticket ticket = new Ticket(
					airlineName,
					passenger.getName(),
					passenger.getAgeGroup(),
					passenger.getGender(),
					passenger.getMealOption(),
					bookingDetails.getSource(),
					bookingDetails.getDestination(),
					schedule.getFlightDate(),
					schedule.getStartTime(),
					schedule.getTicketCost(),
					pnr,
					userEmail
				);
			tickets.add(ticket);
		}
		
		tickets = ticketRepository.saveAll(tickets);
		schedule.setNumberOfVacantSeats(schedule.getNumberOfVacantSeats() - adultAndChildPassengerCount);
		scheduleRepository.save(schedule);

		
		return new ResponseEntity<>(tickets, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<Ticket>> getBookingHistory(String userEmail) {
		return new ResponseEntity<>(ticketRepository.findByBookingUserEmail(userEmail), HttpStatus.OK);
	}

}
