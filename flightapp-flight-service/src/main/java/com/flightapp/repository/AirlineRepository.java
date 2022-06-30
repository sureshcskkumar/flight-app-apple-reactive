package com.flightapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flightapp.entity.Airline;

public interface AirlineRepository extends JpaRepository<Airline, Long> {

	List<Airline> findByName(String name);
}
