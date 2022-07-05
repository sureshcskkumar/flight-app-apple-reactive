package com.flightapp.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.flightapp.entity.Schedule;

import reactor.core.publisher.Flux;

public interface ScheduleRepository extends ReactiveMongoRepository<Schedule, String> {
	
	Flux<Schedule> findByAirlineId(String airlineId);

}
