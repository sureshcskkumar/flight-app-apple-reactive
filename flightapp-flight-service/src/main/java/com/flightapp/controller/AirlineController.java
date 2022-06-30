package com.flightapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flightapp.entity.Airline;
import com.flightapp.entity.Schedule;
import com.flightapp.service.AirlineService;

@RestController
@CrossOrigin
@RequestMapping("/flight")
public class AirlineController {
	
	@Autowired
	private AirlineService airlineService;

	@PostMapping("/airline/register")
	public ResponseEntity<Airline> registerAirline(@RequestBody Airline airline) {
		return airlineService.registerAirline(airline);
	}

	@GetMapping("/airline/list")
	public ResponseEntity<List<Airline>> listAirline() {
		return airlineService.findAll();
	}

	@GetMapping("/airline/search/{airlineId}")
	public ResponseEntity<Airline> findAirlineByName(@PathVariable long airlineId) {
		return airlineService.findById(airlineId);
	}
	
	@GetMapping("/airline/searchByName/{airlineName}")
	public ResponseEntity<List<Airline>> findAirlineByName(@PathVariable String airlineName) {
		return airlineService.findByName(airlineName);
	}


	@PutMapping("/airline/update")
	public ResponseEntity<Airline> updateAirline(@RequestBody Airline airline) {
		return airlineService.updateAirline(airline);
	}

	/*
	@DeleteMapping("/airline/delete/{airlineId}")
	public ResponseEntity<String> deleteAirline(@PathVariable long airlineId) {
		return airlineService.deleteAirline(airlineId);
	}
	*/
	

	//*************************************************************************
	@PutMapping("/airline/block/{airlineId}")
	public ResponseEntity<Airline> blockAirline(@RequestBody Airline airline) {
		return airlineService.registerAirline(airline);
	}
	
	@PutMapping("/airline/unblock/{airlineId}")
	public ResponseEntity<Airline> unblockAirline(@RequestBody Airline airline) {
		return airlineService.registerAirline(airline);
	}
	//*************************************************************************

}
