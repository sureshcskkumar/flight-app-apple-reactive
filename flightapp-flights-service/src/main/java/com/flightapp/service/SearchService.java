package com.flightapp.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.flightapp.entity.Schedule;
import com.flightapp.exception.InvalidSearchException;
import com.flightapp.model.SearchResultEntity;
import com.flightapp.repository.ScheduleRepository;

import reactor.core.publisher.Flux;

@Service
public class SearchService {
	
	@Autowired
	private ScheduleRepository scheduleRepository;

	public Flux<SearchResultEntity> search(String fromPlace, String toPlace, String flightDate, String returnDate) throws InvalidSearchException {
		
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
		
		Flux<SearchResultEntity>
	}
	
	public Flux<Schedule> search(String fromPlace, String toPlace, String flightDate) {
		
		
		
		return null;
	}

}
