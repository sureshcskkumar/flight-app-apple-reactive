package com.flightapp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import com.flightapp.entity.Schedule;
import com.flightapp.entity.Ticket;
import com.flightapp.model.Passenger;
import com.flightapp.repository.TicketRepository;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
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
	

	public Flux<Ticket> bookTicketsUsingPassengers(String scheduleId, String bookingUserEmail, List<Passenger> passengers) {
		
		String url = UriComponentsBuilder.fromHttpUrl(FLIGHTS_URL)
						.pathSegment(scheduleId)			
						.buildAndExpand().toString();
		
		return webClient.get()
				.uri(url)
				.retrieve()
				.bodyToMono(Schedule.class)
				.filter(schedule -> schedule.getNumberOfVacantSeats() > passengers.size())
				.flatMapMany(schedule -> {
					List<Ticket> tickets = passengers.stream()
						.map(p -> {
							Ticket t = new Ticket();
							
							t.setName(p.getName());
							t.setGender(p.getGender());
							t.setAgeGroup(p.getAgeGroup());
							t.setMealOption(p.getMealOption());
							
							t.setScheduleId(scheduleId);
							t.setAirlineId(schedule.getAirlineId());
							t.setSource(schedule.getSource());
							t.setDestination(schedule.getDestination());
							t.setFlightDate(schedule.getFlightDate());
							t.setFlightTime(schedule.getStartTime());
							t.setCost(schedule.getTicketCost());
							t.setBookingUserEmail(bookingUserEmail);
							
							return t;
						}).collect(Collectors.toList());

					schedule.setNumberOfVacantSeats(schedule.getNumberOfVacantSeats() - passengers.size());
					
					webClient.put()
						.uri(url)
						.body(Mono.just(schedule), Schedule.class)
						.retrieve()
						.bodyToMono(Schedule.class)
						.subscribe()
						;
					
					Flux<Ticket> ticketFlux = ticketRepository.saveAll(tickets).map(t-> {
						t.setPnr(t.getId());
						return t;
						});
					return ticketRepository.saveAll(ticketFlux);
				})
				.log();

	}



	public Flux<Ticket> bookTicketsUsingTickets(String scheduleId, String bookingUserEmail, List<Ticket> tickets) {
		
		String url = UriComponentsBuilder.fromHttpUrl(FLIGHTS_URL)
						.pathSegment(scheduleId)			
						.buildAndExpand().toString();
		
		return webClient.get()
				.uri(url)
				.retrieve()
				.bodyToMono(Schedule.class)
				.filter(schedule -> schedule.getNumberOfVacantSeats() > tickets.size())
				.flatMapMany(schedule -> {
					tickets.forEach(t-> {
						t.setScheduleId(scheduleId);
						t.setAirlineId(schedule.getAirlineId());
						t.setSource(schedule.getSource());
						t.setDestination(schedule.getDestination());
						t.setFlightDate(schedule.getFlightDate());
						t.setFlightTime(schedule.getStartTime());
						t.setCost(schedule.getTicketCost());
						t.setBookingUserEmail(bookingUserEmail);
					});
					Flux<Ticket> ticketFlux = ticketRepository.saveAll(tickets).map(t-> {
						t.setPnr(t.getId());
						return t;
						});
					
					schedule.setNumberOfVacantSeats(schedule.getNumberOfVacantSeats() - tickets.size());
					
					webClient.put()
						.uri(url)
						.body(Mono.just(schedule), Schedule.class)
						.retrieve()
						.bodyToMono(Schedule.class)
						.subscribe()
						;
					
					return ticketRepository.saveAll(ticketFlux);
				})
				.log();

	}


	
	public Flux<Ticket> bookTicketsOld(String scheduleId, String bookingUserEmail, List<Ticket> tickets) {
		
		String url = UriComponentsBuilder.fromHttpUrl(FLIGHTS_URL)
						.pathSegment(scheduleId)			
						.buildAndExpand().toString();
		
		
		System.out.println("Webclient call start");
		
		Mono<Schedule> scheduleMono = webClient.get()
										.uri(url)
										.retrieve()
						                .onStatus(HttpStatus::is4xxClientError, (clientResponse -> {
						                    log.info("Status code : {}", clientResponse.statusCode().value());
						                    if (clientResponse.statusCode().equals(HttpStatus.NOT_FOUND)) {
						                        return Mono.error(new Exception("No schedule for the passed id is available : " + clientResponse.statusCode().value()));
						                    }
						                    return clientResponse.bodyToMono(String.class)
						                            .flatMap(response -> Mono.error(new Exception(response + clientResponse.statusCode().value())));
						                }))
						                .onStatus(HttpStatus::is5xxServerError, (clientResponse -> {
						                    log.info("Status code : {}", clientResponse.statusCode().value());
						                    return clientResponse.bodyToMono(String.class)
						                            .flatMap(response -> Mono.error(new Exception(response)));
						                }))
						                .onStatus( x->{
						                	log.info("Status code : {}", x.value());
						                	return true;
						                	}, clientResponse -> {
						                		log.info("Status code : {}", clientResponse.statusCode().value());
						                		if (clientResponse.statusCode().equals(HttpStatus.NOT_FOUND)) {
							                        return Mono.error(new Exception("No schedule for the passed id is available : " + clientResponse.statusCode().value()));
							                    }
						                		return clientResponse.bodyToMono(String.class)
							                            .flatMap(response -> Mono.error(new Exception(response + clientResponse.statusCode().value())));
						                	})
										.bodyToMono(Schedule.class)
										.log();

		System.out.println("Before subscribe() call");
		scheduleMono.subscribe();
		System.out.println("After subscribe() call");

		System.out.println("Webclient call end");
		
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
