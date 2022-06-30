package com.flightapp.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.flightapp.entity.Airline;
import com.flightapp.entity.Schedule;

public interface AirlineService {

	ResponseEntity<Airline> registerAirline(Airline airline);

	ResponseEntity<List<Airline>> findAll();

	ResponseEntity<List<Airline>> findByName(String airlineName);

	ResponseEntity<String> deleteAirline(long airlineId);

	ResponseEntity<Airline> updateAirline(Airline airline);

	ResponseEntity<Airline> findById(long airlineId);

}