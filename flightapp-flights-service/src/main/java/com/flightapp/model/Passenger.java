package com.flightapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Passenger {

	private String name;

	private Gender gender;

	private AgeGroup ageGroup;
	
	private MealOption mealOption;

}
