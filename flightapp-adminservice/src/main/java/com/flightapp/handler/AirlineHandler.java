package com.flightapp.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.flightapp.entity.Airline;
import com.flightapp.repository.AirlineRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AirlineHandler {
	
	@Autowired
	private AirlineRepository airlineRepository;
	
	public Mono<ServerResponse>  getAirlines(ServerRequest request) {
		Flux<Airline> airlineFlux = airlineRepository.findAll();
		return ServerResponse.ok().body(airlineFlux, Airline.class);
	}

	public Mono<ServerResponse> addAirline(ServerRequest request) {
		return request.bodyToMono(Airline.class)
					.flatMap(airlineRepository::save)
					.flatMap(ServerResponse.status(HttpStatus.CREATED)::bodyValue);
			
	}
	
	public Mono<ServerResponse> getAirlineById(ServerRequest request) {
		String airlineId = request.pathVariable("airlineId");
		return airlineRepository.findById(airlineId)
					.flatMap(ServerResponse.ok()::bodyValue);
	}
	
	public Mono<ServerResponse> updateAirline(ServerRequest request) {
		
		String airlineId = request.pathVariable("airlineId");
		
		return null;
	}

}
