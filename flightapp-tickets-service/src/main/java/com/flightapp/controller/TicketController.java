package com.flightapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flightapp.entity.Ticket;
import com.flightapp.model.Passenger;
import com.flightapp.service.TicketService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/tickets")
public class TicketController {

	@Autowired
	private TicketService ticketService;
	
	@GetMapping("/booking/history")
	public Flux<Ticket> getAllTickets() {
		return ticketService.getAllTickets();
	}
	
	@PostMapping("/booking/searchByPNR/{pnr}")
	public Mono<Ticket> getTicketByPNR(@PathVariable String pnr) {
		return ticketService.getTicketByPNR(pnr);
	}
	
	@PostMapping("/booking/cancelByPNR/{pnr}")
	public Mono<Void> cancelTicketByPNR(@PathVariable String pnr) {
		return ticketService.cancelTicketByPNR(pnr);
	}
	
	@PostMapping("/booking/{scheduleId}")
	public Flux<Ticket> bookTickets(@PathVariable String scheduleId,@RequestBody List<Ticket> tickets) {
		return ticketService.bookTickets(scheduleId, tickets);
	}
	
}
