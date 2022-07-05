package com.flightapp.model;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Schedule {

	private long id;

	private String airlineId;

	private String source;

	private String destination;

	private LocalDate flightDate;

	private LocalTime startTime;

	private LocalTime endTime;

	private int numberOfSeats;
	
	private int numberOfVacantSeats;
	
	private double ticketCost;

}