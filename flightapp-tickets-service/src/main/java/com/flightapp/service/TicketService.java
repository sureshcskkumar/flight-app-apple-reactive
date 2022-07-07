package com.flightapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import com.flightapp.entity.Ticket;
import com.flightapp.model.Passenger;
import com.flightapp.model.Schedule;
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

	public Flux<Ticket> bookTickets(String scheduleId, List<Ticket> tickets) {
		
		String url = UriComponentsBuilder.fromHttpUrl(FLIGHTS_URL)
						.path(scheduleId)				
						.buildAndExpand().toString();
		
		Mono<Schedule> scheduleMono = webClient.get()
										.uri(url)
										.retrieve()
										.bodyToMono(Schedule.class);
		
		Mono<Schedule> scheduleAfterTicketsBooked = scheduleMono.map(schedule -> {
			int currentNumberOfVacantSeats = schedule.getNumberOfVacantSeats();
			schedule.setNumberOfVacantSeats(currentNumberOfVacantSeats - tickets.size());
			return schedule;
		});
		
		webClient.put()
			.uri(url)
			.body(scheduleAfterTicketsBooked, Schedule.class)
			.retrieve()
			.bodyToMono(Schedule.class);
			
		Flux<Ticket> ticketFlux = ticketRepository.saveAll(tickets);
		return ticketFlux;
	}
}
