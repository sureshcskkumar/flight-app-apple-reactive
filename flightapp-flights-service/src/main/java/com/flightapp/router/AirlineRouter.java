package com.flightapp.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.flightapp.handler.AirlineHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class AirlineRouter {

    @Bean
    public RouterFunction<ServerResponse> airlineRoute(AirlineHandler airlineHandler) {
        return route()
                .nest(path("/api/v1/airlines"), builder ->
                        builder
                                .GET("", airlineHandler::getAirlines)
                                .GET("/{id}", airlineHandler::getAirlineById)
                                .POST("", airlineHandler::addAirline)
                                .PUT("/{id}", airlineHandler::updateAirline)
                                // .DELETE("/{id}", airlineHandler::deleteAirline)
                                )
                .build();
    }
}