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
                                .GET("/{airlineId}", airlineHandler::getAirlineById)
                                .POST("", airlineHandler::addAirline)
                                .PUT("/{airlineId}", airlineHandler::updateAirline)
                                .DELETE("/{airlineId}", airlineHandler::deleteAirline)
                                // .GET("/stream", airlineHandler::getReviewsStream)
                                )
                // .GET("/v1/helloworld", (request -> ServerResponse.ok().bodyValue("HelloWorld")))
                // .GET("/v1/greeting/{name}", (request -> ServerResponse.ok().bodyValue("hello " + request.pathVariable("name"))))
                //  .GET("/v1/reviews",reviewsHandler::getReviews)
                // .POST("/v1/reviews", reviewsHandler::addReview)
                .build();
    }
}