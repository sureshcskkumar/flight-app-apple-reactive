package com.flightapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flightapp.model.Airline;
import com.flightapp.service.AirlineService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@CrossOrigin
@RequestMapping("/api/v1.0")
@SecurityRequirement(name = "appSecurity")
public class AirlineController {
	
	@Autowired
	private AirlineService airlineService;

	/*
	@PostMapping("/flight/airline/register")
	public ResponseEntity<Airline> registerAirline(final HttpEntity<Airline> data) {
		return airlineService.registerAirline(data);
	}
	*/
	
	@PostMapping("/flight/airline/register")
	public ResponseEntity<Airline> registerAirline(@RequestBody Airline airline, @RequestHeader MultiValueMap<String,String> headers) {
		return airlineService.registerAirline(airline, headers);
	}
	
}
