package com.flightapp.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationConfiguration {

	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	@LoadBalanced // Annotation is needed if used with eureka url, but not needed if used with gatewayurl
	public RestTemplate getRestTemplate(){
		return new RestTemplate();
	}
}
