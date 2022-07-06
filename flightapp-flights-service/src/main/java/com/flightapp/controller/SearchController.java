package com.flightapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.flightapp.entity.Schedule;
import com.flightapp.exception.InvalidSearchException;
import com.flightapp.model.SearchResultEntity;
import com.flightapp.service.SearchService;

import reactor.core.publisher.Flux;

@RestController
@ControllerAdvice
public class SearchController {
	
	@Autowired
	private SearchService searchService;
	
	@GetMapping("/search/{fromPlace}/{toPlace}/{flightDate}/{returnDate}")
	public Flux<SearchResultEntity> search(
			@PathVariable("fromPlace") String fromPlace,
			@PathVariable("toPlace") String toPlace,
			@PathVariable("flightDate") String flightDate,
			@PathVariable(value = "returnDate", required = false) String returnDate
			) throws InvalidSearchException {
		return searchService.search(fromPlace, toPlace, flightDate, returnDate);
	}

}
