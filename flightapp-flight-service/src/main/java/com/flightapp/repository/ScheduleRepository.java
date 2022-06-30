package com.flightapp.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.flightapp.entity.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

	public List<Schedule> findByAirlineId(long airlineId);
	
	@Query("select s from Schedule s where s.airline.id = ?1 and s.flightDate = ?2 and s.startTime = ?3")
	public List<Schedule> findByAirlineIdAndDateTime(long airlineId, LocalDate date, LocalTime time);
	
	public List<Schedule> findByFlightDate(LocalDate date);

	@Query("select s from Schedule s where s.source = ?1 and s.destination = ?2 and s.flightDate = ?3")
	public List<Schedule> searchFlights(String fromPlace, String toPlace, LocalDate flightDate);
}
