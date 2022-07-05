package com.flightapp.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.flightapp.entity.Schedule;
import com.flightapp.repository.AirlineRepository;
import com.flightapp.repository.ScheduleRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ScheduleHandler {
	
	@Autowired
	private ScheduleRepository scheduleRepository;
	
	@Autowired
	private AirlineRepository airlineRepository;
	
	public Mono<ServerResponse>  getSchedules(ServerRequest request) {
		Flux<Schedule> scheduleFlux = scheduleRepository.findAll();
		return ServerResponse.ok().body(scheduleFlux, Schedule.class);
	}

	public Mono<ServerResponse> addSchedule(ServerRequest request) {
		return request.bodyToMono(Schedule.class)
					// .flatMap(scheduleRepository::save)
					.flatMap(schedule -> {
						return scheduleRepository.save(schedule);
						})
					.flatMap(ServerResponse.status(HttpStatus.CREATED)::bodyValue);
			
	}
	
	public Mono<ServerResponse> getScheduleById(ServerRequest request) {
		String scheduleId = request.pathVariable("id");
		return scheduleRepository.findById(scheduleId)
					.flatMap(ServerResponse.ok()::bodyValue)
					.switchIfEmpty(ServerResponse.notFound().build());
	}
	
	public Mono<ServerResponse> updateSchedule(ServerRequest request) {
		
		String scheduleId = request.pathVariable("id");
		
		Mono<Schedule> scheduleFromDB = scheduleRepository.findById(scheduleId);

		return scheduleFromDB.flatMap(schedule -> request.bodyToMono(Schedule.class)
					.map(requestSchedule -> {
						schedule.setId(requestSchedule.getId());
						schedule.setAirlineId(requestSchedule.getAirlineId());
						schedule.setSource(requestSchedule.getSource());
						schedule.setDestination(requestSchedule.getDestination());
						schedule.setFlightDate(requestSchedule.getFlightDate());
						schedule.setStartTime(requestSchedule.getStartTime());
						schedule.setEndTime(requestSchedule.getEndTime());
						schedule.setNumberOfSeats(requestSchedule.getNumberOfSeats());
						schedule.setNumberOfVacantSeats(requestSchedule.getNumberOfVacantSeats());
						schedule.setTicketCost(requestSchedule.getTicketCost());
						return schedule;
					})
					.flatMap(scheduleRepository::save)
					.flatMap(savedSchedule -> ServerResponse.ok().bodyValue(savedSchedule))
					)
					.switchIfEmpty(ServerResponse.notFound().build());
	}

	public Mono<ServerResponse> deleteSchedule(ServerRequest request) {
		String scheduleId = request.pathVariable("id");
		return scheduleRepository.findById(scheduleId)
				.flatMap(schedule -> scheduleRepository.deleteById(scheduleId))
				.then(ServerResponse.noContent().build());
	}
	
	
	public Mono<ServerResponse> getScheduleByAirlineId(ServerRequest request) {
		String airlineId = request.pathVariable("airlineId");
		Flux<Schedule> scheduleFlux = scheduleRepository.findByAirlineId(airlineId);
		return ServerResponse.ok().body(scheduleFlux, Schedule.class);
	}

}
