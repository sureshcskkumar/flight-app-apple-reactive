package com.flightapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flightapp.entity.Schedule;
import com.flightapp.service.ScheduleService;

@RestController
@CrossOrigin
@RequestMapping("/flight")
public class ScheduleController {

	@Autowired
	private ScheduleService scheduleService;
	
	@PostMapping("/airline/schedule/add")
	public ResponseEntity<Schedule> addSchedule(@RequestBody Schedule schedule){
		return scheduleService.addSchedule(schedule);
	}
	
	@GetMapping("/airline/getschedules")
	public ResponseEntity<List<Schedule>> getSchedules(){
		return scheduleService.getSchedules();
	}
}
