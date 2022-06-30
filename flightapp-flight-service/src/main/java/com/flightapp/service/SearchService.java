package com.flightapp.service;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;

import com.flightapp.model.SearchResult;

public interface SearchService {

	ResponseEntity<SearchResult> search(String fromPlace, String toPlace, LocalDate flightDate, LocalDate returnDate);

}
