package com.flightapp.router;

import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.flightapp.handler.ScheduleHandler;

@Configuration
public class ScheduleRouter {

	@Bean
    public RouterFunction<ServerResponse> scheduleRoute(ScheduleHandler scheduleHandler) {
        return route()
                .nest(path("/api/v1/schedules"), builder ->
                        builder
                                .GET("", scheduleHandler::getSchedules)
                                .GET("/{id}", scheduleHandler::getScheduleById)
                                .POST("", scheduleHandler::addSchedule)
                                .PUT("/{id}", scheduleHandler::updateSchedule)
                                // .DELETE("/{id}", scheduleHandler::deleteSchedule)
                                .GET("/forAirline/{airlineId}", scheduleHandler::getScheduleById)
                                )
                .build();
    }
}