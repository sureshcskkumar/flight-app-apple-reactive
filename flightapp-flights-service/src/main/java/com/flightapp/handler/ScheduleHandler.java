package com.flightapp.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
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
    private KafkaTemplate<String, Schedule> kafkaTemplate;
	
	@Autowired
	private AirlineRepository airlineRepository;
	
	private static final String TOPIC = "schedule-delete-topic";
	
	public Mono<ServerResponse>  getSchedules(ServerRequest request) {
		Flux<Schedule> scheduleFlux = scheduleRepository.findAll();
		return ServerResponse.ok().body(scheduleFlux, Schedule.class);
	}

	
	public Mono<ServerResponse> addSchedule(ServerRequest request) {
		return request.bodyToMono(Schedule.class)
					.flatMap(schedule -> {
						return airlineRepository.findById(schedule.getAirlineId())
							.flatMap(airline->{
									return scheduleRepository.findByAirlineId(schedule.getAirlineId())
										.hasElements()
										.flatMap(isFlightHasAnySchedule -> {
											if (isFlightHasAnySchedule) {
												return scheduleRepository.findByAirlineId(schedule.getAirlineId())
												.filter(s->{
													return s.getFlightDate().equals(schedule.getFlightDate()) &&
															s.getStartTime().equals(schedule.getStartTime());
												})
												.hasElements()
												.flatMap(isFlightHasScheduleAtTime->{
													if(isFlightHasScheduleAtTime) {
														return Mono.empty();
													}
													return scheduleRepository.save(schedule);
												});
												
											} else {
												return scheduleRepository.save(schedule);
											}
										});
								});
						})
					.flatMap(ServerResponse.status(HttpStatus.CREATED)::bodyValue)
					.switchIfEmpty(ServerResponse.status(HttpStatus.BAD_REQUEST).bodyValue("Choose a different airline or a different time for the same airline"));
			
	}
	
	public Mono<ServerResponse> addScheduleOld2(ServerRequest request) {
		return request.bodyToMono(Schedule.class)
					.flatMap(schedule -> {
						return airlineRepository.findById(schedule.getAirlineId())
							.flatMap(airline->{
								return scheduleRepository.findByAirlineId(schedule.getAirlineId())
									.filter(s->(s.getStartTime().equals(schedule.getStartTime())))
									.hasElements()
									.flatMap(alreadyScheduled->{
										if (!alreadyScheduled) {
											return scheduleRepository.save(schedule);
										}else {
											return Mono.empty();
										}
									});
									//.flatMap(scheduleRepository::save);
									
										// return scheduleRepository.save(schedule);
										// return schedule;
								}).switchIfEmpty(scheduleRepository.save(schedule));
						})
					.flatMap(ServerResponse.status(HttpStatus.CREATED)::bodyValue)
					.switchIfEmpty(ServerResponse.status(HttpStatus.BAD_REQUEST).build());
			
	}

	
	public Mono<ServerResponse> addScheduleOld1(ServerRequest request) {
		return request.bodyToMono(Schedule.class)
				.flatMap(scheduleRepository::save)
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
		System.out.println("Schedule with following id is deleted: " + scheduleId);
		
		return scheduleRepository.findById(scheduleId)
				.flatMap(schedule -> {
					
					kafkaTemplate.send(TOPIC,schedule);
					System.out.println("Kafka Producer has sent topic");
					return scheduleRepository.deleteById(scheduleId);
				})
				.then(ServerResponse.noContent().build());
	}
	
	
	public Mono<ServerResponse> getScheduleByAirlineId(ServerRequest request) {
		String airlineId = request.pathVariable("airlineId");
		Flux<Schedule> scheduleFlux = scheduleRepository.findByAirlineId(airlineId);
		return ServerResponse.ok().body(scheduleFlux, Schedule.class);
	}

}
