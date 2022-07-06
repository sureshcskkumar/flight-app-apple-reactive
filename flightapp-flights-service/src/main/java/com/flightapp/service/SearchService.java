package com.flightapp.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flightapp.entity.Schedule;
import com.flightapp.repository.ScheduleRepository;

import reactor.core.publisher.Flux;

@Service
public class SearchService {
	
	@Autowired
	private ScheduleRepository scheduleRepository;

	public Flux<Schedule> search(String fromPlace, String toPlace, LocalDate flightDate, LocalDate returnDate) {

		Flux<Schedule> searchResultFlux = search(fromPlace,toPlace)
				.filter(schedule -> {
					if(flightDate.equals(schedule.getFlightDate())) {
						System.out.println("Schedule: " + schedule.toString());
						return true;
					} else {
						System.out.println("Schedule: " + schedule.toString());
						return false;
					}
						
				}).log();
		Flux<Schedule> returnSearchResultFlux = null;
		if (null != returnDate) {
			returnSearchResultFlux = search(toPlace,fromPlace)
					.filter(schedule -> {
						return returnDate.equals(schedule.getFlightDate());
					}).log();
			searchResultFlux = searchResultFlux.concatWith(returnSearchResultFlux);
		}
		return searchResultFlux;
			// .map(schedule -> {
			// 		return new SearchResultEntity(schedule);
			// }).log();
	}
	
	public Flux<Schedule> search(String fromPlace, String toPlace) {

		return scheduleRepository.findBySourceAndDestination(fromPlace, toPlace);
	}

}
