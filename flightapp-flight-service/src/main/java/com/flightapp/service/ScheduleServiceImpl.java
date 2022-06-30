package com.flightapp.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.flightapp.entity.Schedule;
import com.flightapp.repository.ScheduleRepository;

@Service
public class ScheduleServiceImpl implements ScheduleService {
	
	@Autowired
	private ScheduleRepository scheduleRepository;
	
	@Override
	public ResponseEntity<Schedule> addSchedule(Schedule schedule) {

		Objects.requireNonNull(schedule);
		List<Schedule> scheduleList = scheduleRepository.findByAirlineIdAndDateTime(
				schedule.getAirline().getId(),
				schedule.getFlightDate(),
				schedule.getStartTime());
		if (scheduleList != null && scheduleList.size()>0) {
			return new ResponseEntity("Schedule already exists for the airline at the requested dtae and time! Please choose a different date or time", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(scheduleRepository.save(schedule), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<Schedule>> getSchedules() {
		return new ResponseEntity<>(scheduleRepository.findAll(), HttpStatus.OK);
	}
}
