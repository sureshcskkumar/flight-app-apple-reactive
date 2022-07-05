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
		String airlineId = request.pathVariable("id");
		return airlineRepository.findById(airlineId)
					.flatMap(ServerResponse.ok()::bodyValue)
					.switchIfEmpty(ServerResponse.notFound().build());
	}
	
	public Mono<ServerResponse> updateAirline(ServerRequest request) {
		
		String airlineId = request.pathVariable("id");
		
		Mono<Airline> airlineFromDB = airlineRepository.findById(airlineId);

		return airlineFromDB.flatMap(airline -> request.bodyToMono(Airline.class)
					.map(requestAirline -> {
						airline.setName(requestAirline.getName());
						airline.setBlocked(requestAirline.isBlocked());
						airline.setContactNumber(requestAirline.getContactNumber());
						return airline;
					})
					.flatMap(airlineRepository::save)
					.flatMap(savedAirline -> ServerResponse.ok().bodyValue(savedAirline))
					)
					.switchIfEmpty(ServerResponse.notFound().build());
		
		/*
		Mono<Airline> airlineFromRequest = 
				request.bodyToMono(Airline.class)
							.map(a->{
								a.setId(airlineId);
								return a;
							});
		*/
		/*
		 airlineFromDB.flatmap(airline -> request.bodyToMono(Airline.class)
				.map(reqAirline -> {
					airline.setName(reqAirline.getName());
					airline.setBlocked(reqAirline.isBlocked());
					airline.setContactNumber(reqAirline.getContactNumber());
					return airline;
				})
				.flatMap(airlineRepository::save)
				.flatMap(savedAirline -> ServerResponse.ok().bodyValue(savedAirline)))
				// .switchIfEmpty(ServerResponse.notFound().build())
				;
			*/
		
	}

	public Mono<ServerResponse> deleteAirline(ServerRequest request) {
		String airlineId = request.pathVariable("id");
		return airlineRepository.findById(airlineId)
				.flatMap(airline -> airlineRepository.deleteById(airlineId))
				.then(ServerResponse.noContent().build());
	}

}
