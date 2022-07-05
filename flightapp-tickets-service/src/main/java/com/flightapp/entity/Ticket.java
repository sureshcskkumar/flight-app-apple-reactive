package com.flightapp.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.flightapp.model.AgeGroup;
import com.flightapp.model.Gender;
import com.flightapp.model.MealOption;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Document(collection = "tickets")
public class Ticket {

	@Id
	private long id;

	private String airlineName;

	private String name;
	
	private AgeGroup ageGroup;
	
	private Gender gender;
	
	private MealOption mealOption;

	private String source;

	private String destination;

	private LocalDate flightDate;

	private LocalTime flightTime;
	
	private double cost;
	
	private UUID pnr;
	
	@JsonIgnore
	private String bookingUserEmail;

	public Ticket(String airlineName, String name, AgeGroup ageGroup, Gender gender, MealOption mealOption,
			String source, String destination, LocalDate flightDate, LocalTime flightTime, double cost, UUID pnr,
			String bookingUserEmail) {
		this.airlineName = airlineName;
		this.name = name;
		this.ageGroup = ageGroup;
		this.gender = gender;
		this.mealOption = mealOption;
		this.source = source;
		this.destination = destination;
		this.flightDate = flightDate;
		this.flightTime = flightTime;
		this.cost = cost;
		this.pnr = pnr;
		this.bookingUserEmail = bookingUserEmail;
	}

}
