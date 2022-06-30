package com.flightapp.flightappeurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class FlightappEurekaserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlightappEurekaserverApplication.class, args);
	}

}
