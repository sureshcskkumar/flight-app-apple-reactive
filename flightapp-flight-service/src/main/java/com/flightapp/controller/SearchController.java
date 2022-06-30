package com.flightapp.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flightapp.model.SearchResult;
import com.flightapp.service.SearchService;

@RestController
@CrossOrigin
@RequestMapping("/flight")
public class SearchController {

	@Autowired
	private SearchService searchService;
	
	@GetMapping("/search")
	public ResponseEntity<SearchResult> search(
			@RequestParam("fromPlace") String fromPlace,
			@RequestParam("toPlace") String toPlace,
			@RequestParam("flightDate") String flightDate,
			@RequestParam(value = "returnDate", required = false) String returnDate
			){

		LocalDate flightDateAsDate = null;
		LocalDate returnDateAsDate = null;
		try {
			flightDateAsDate = LocalDate.parse(flightDate);
			if (returnDate!=null && !returnDate.isEmpty()) {
				returnDateAsDate = LocalDate.parse(returnDate);
			}
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.OK);
		}
		
		return searchService.search(fromPlace, toPlace, flightDateAsDate, returnDateAsDate);
	}
	
}
