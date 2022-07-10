package com.flightapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.util.UriComponentsBuilder;

import com.flightapp.entity.Ticket;
import com.flightapp.model.Passenger;
import com.flightapp.entity.Schedule;
import com.flightapp.repository.TicketRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TicketService {

	@Autowired
	private TicketRepository ticketRepository;
	
	@Autowired
	private WebClient webClient;
	
	private static final String FLIGHTS_URL = "http://localhost:8082/api/v1/schedules";
	
	public Flux<Ticket> getAllTickets() {
		return ticketRepository.findAll();
	}

	public Mono<Ticket> getTicketByPNR(String pnr) {
		return ticketRepository.findById(pnr);
	}

	public Mono<Void> cancelTicketByPNR(String pnr) {
		return ticketRepository.deleteById(pnr);
	}

	public Flux<Ticket> bookTickets(String scheduleId, String bookingUserEmail, List<Ticket> tickets) {
		
		String url = UriComponentsBuilder.fromHttpUrl(FLIGHTS_URL)
						.pathSegment(scheduleId)			
						.buildAndExpand().toString();
		
		
		
		
		WebClient.ResponseSpec responseSpec = webClient.get()
										.uri(url)
										.retrieve();
		
		Mono<Schedule> scheduleMono =  responseSpec
											.bodyToMono(Schedule.class);
		
		scheduleMono.map(schedule -> {
			System.out.println(schedule);
			return schedule;
		}).log();

		tickets.forEach((ticket)->{
			ticket.setBookingUserEmail(bookingUserEmail);
		});
		Flux<Ticket> ticketFlux = ticketRepository.saveAll(tickets).log();
		
		ticketFlux = ticketFlux.map(ticket -> {
			ticket.setPnr(ticket.getId());
			return ticket;
		}).log();
		
		ticketFlux = ticketFlux.map(ticket -> {
			scheduleMono.map(schedule -> {
				ticket.setAirlineId(schedule.getAirlineId());
				ticket.setSource(schedule.getSource());
				ticket.setDestination(schedule.getDestination());
				ticket.setFlightDate(schedule.getFlightDate());
				ticket.setFlightTime(schedule.getStartTime());
				ticket.setCost(schedule.getTicketCost());
				return ticket;
			}).log();
			return ticket;
		}).log();
		
		ticketFlux = ticketRepository.saveAll(ticketFlux);
		
		Mono<Schedule> scheduleAfterTicketsBooked = scheduleMono.map(schedule -> {
			int currentNumberOfVacantSeats = schedule.getNumberOfVacantSeats();
			schedule.setNumberOfVacantSeats(currentNumberOfVacantSeats - tickets.size());
			return schedule;
		}).log();
		
		webClient.put()
			.uri(url)
			.body(scheduleAfterTicketsBooked, Schedule.class)
			.retrieve()
			.bodyToMono(Schedule.class);
		
		return ticketFlux;
	}
}
