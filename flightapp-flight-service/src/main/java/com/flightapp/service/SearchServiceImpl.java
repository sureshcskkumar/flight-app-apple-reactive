package com.flightapp.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.flightapp.entity.Schedule;
import com.flightapp.model.SearchResult;
import com.flightapp.model.SearchResultEntity;
import com.flightapp.repository.AirlineRepository;
import com.flightapp.repository.ScheduleRepository;

@Service
public class SearchServiceImpl implements SearchService {

	@Autowired
	private ScheduleRepository scheduleRepository;

	@Override
	public ResponseEntity<SearchResult> search(String fromPlace, String toPlace, LocalDate flightDate, LocalDate returnDate) {

		List<SearchResultEntity> flightSearchEntities = searchResult(fromPlace, toPlace, flightDate);
		
		List<SearchResultEntity> returnSearchEntities = null;
		if (returnDate!=null) {
			returnSearchEntities = searchResult(toPlace, fromPlace, returnDate);
		}
		
		return new ResponseEntity<>(new SearchResult(fromPlace, toPlace, flightDate, returnDate, flightSearchEntities, returnSearchEntities), HttpStatus.OK);
		
	}
	
	private List<SearchResultEntity> searchResult(String fromPlace, String toPlace, LocalDate flightDate) {
		List<Schedule> flightSchedules = scheduleRepository.searchFlights(fromPlace, toPlace, flightDate);

		List<SearchResultEntity> searchResulList = new ArrayList<>();
		if (flightSchedules != null && flightSchedules.size() > 0) {
			for (Schedule schedule : flightSchedules) {
				if (!schedule.getAirline().isBlocked()) {
					searchResulList.add(new SearchResultEntity(schedule));
				}
			}
		}
		return searchResulList;
	}
	
}
