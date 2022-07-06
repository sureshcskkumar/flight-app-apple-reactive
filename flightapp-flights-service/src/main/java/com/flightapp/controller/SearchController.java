package com.flightapp.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flightapp.entity.Schedule;
import com.flightapp.exception.InvalidSearchException;
import com.flightapp.service.SearchService;

import reactor.core.publisher.Flux;

@RestController
@ControllerAdvice
@RequestMapping("/api/v1")
public class SearchController {
	
	@Autowired
	private SearchService searchService;
	
	@GetMapping(value = {"/search/{fromPlace}/{toPlace}/{flightDate}",
			"/search/{fromPlace}/{toPlace}/{flightDate}/{returnDate}"})
	public Flux<Schedule> search(
			@PathVariable("fromPlace") String fromPlace,
			@PathVariable("toPlace") String toPlace,
			@PathVariable("flightDate") String flightDate,
			@PathVariable(value = "returnDate", required = false) String returnDate
			) throws InvalidSearchException {
		
		LocalDate flightDateAsDate = null;
		LocalDate returnDateAsDate = null;
		try {
			flightDateAsDate = LocalDate.parse(flightDate);
			if (returnDate!=null && !returnDate.isEmpty()) {
				returnDateAsDate = LocalDate.parse(returnDate);
			}
		} catch (Exception e) {
			throw new InvalidSearchException("Invalid date. Please enter a correct date!", e);
		}
		
		return searchService.search(fromPlace, toPlace, flightDateAsDate, returnDateAsDate);
	}

}
