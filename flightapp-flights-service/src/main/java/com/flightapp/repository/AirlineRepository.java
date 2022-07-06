package com.flightapp.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.flightapp.entity.Airline;

import reactor.core.publisher.Flux;

public interface AirlineRepository extends ReactiveMongoRepository<Airline, String> {
	
	Flux<Airline> findByName(String name);

}
