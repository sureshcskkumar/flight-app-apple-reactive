package com.flightapp.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.flightapp.model.AgeGroup;
import com.flightapp.model.Gender;
import com.flightapp.model.MealOption;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tickets")
@Getter
@Setter
@NoArgsConstructor
public class Ticket {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String airlineName;

	private String name;
	
	@Enumerated(EnumType.STRING)
	private AgeGroup ageGroup;
	
	@Enumerated(EnumType.STRING)
	private Gender gender;
	
	@Enumerated(EnumType.STRING)
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
