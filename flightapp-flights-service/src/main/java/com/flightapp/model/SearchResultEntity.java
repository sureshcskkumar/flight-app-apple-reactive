package com.flightapp.model;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flightapp.entity.Schedule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResultEntity {

	private String scheduleId;
	
	private String airlineId;
	
	private LocalTime startTime;
	
	private LocalTime endTime;
	
	private double ticketCost;

	public SearchResultEntity(Schedule schedule) {
		this.scheduleId = schedule.getId();
		//this.airlineName = schedule.getAirline().getName();
		this.airlineId = schedule.getAirlineId();
		this.startTime = schedule.getStartTime();
		this.endTime = schedule.getEndTime();
		this.ticketCost = schedule.getTicketCost();
	}

}
