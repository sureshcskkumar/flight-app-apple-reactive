package com.flightapp.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.flightapp.entity.Schedule;

public interface ScheduleRepository extends ReactiveMongoRepository<Schedule, String> {

}
