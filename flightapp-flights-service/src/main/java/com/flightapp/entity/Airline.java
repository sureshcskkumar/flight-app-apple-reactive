package com.flightapp.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(collection = "airlines")
public class Airline {

	@Id
	private String id;
	
	private String name;
	
	private String contactNumber;

	private boolean blocked;
	
	// @JsonIgnore
	// private List<Schedule> schedules;

}
