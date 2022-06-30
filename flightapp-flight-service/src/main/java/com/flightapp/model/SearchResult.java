package com.flightapp.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResult {

	private String source;
	
	private String destination;
	
	private LocalDate departureDate;
	
	private LocalDate returnDate;
	
	private List<SearchResultEntity> departureFlightResultList;
	
	private List<SearchResultEntity> returnFlightResultList;
	
}
