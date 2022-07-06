package com.flightapp.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "schedules")
public class Schedule {

	@Id
	private String id;

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
