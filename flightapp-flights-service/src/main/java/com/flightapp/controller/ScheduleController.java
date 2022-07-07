package com.flightapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flightapp.entity.Schedule;
import com.flightapp.repository.ScheduleRepository;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/schedules")
public class ScheduleController {
	
	@Autowired
	private ScheduleRepository scheduleRepository;
	
	@GetMapping("/{id}")
	public Mono<Schedule> getScheduleById(@PathVariable String id){
		return scheduleRepository.findById(id);
	}

}
