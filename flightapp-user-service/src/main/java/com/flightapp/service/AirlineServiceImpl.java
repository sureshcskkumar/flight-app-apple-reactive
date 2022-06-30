package com.flightapp.service;

import static com.flightapp.config.ApplicationConstants.AIRLINE_REGISTER;
import static com.flightapp.config.ApplicationConstants.FLIGHTAPPURL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.flightapp.model.Airline;

@Service
public class AirlineServiceImpl implements AirlineService {

	@Autowired
    private RestTemplate template;
	
	@Override
	public ResponseEntity<Airline> registerAirline(HttpEntity<Airline> data) {

		try {

			String url = FLIGHTAPPURL + AIRLINE_REGISTER;
			ResponseEntity<Airline> response = template.exchange(url, HttpMethod.POST, data, Airline.class);
			return response;
		} catch (HttpStatusCodeException e) {
			return new ResponseEntity(e.getMessage(), e.getStatusCode());
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	@Override
	public ResponseEntity<Airline> registerAirline(Airline airline, MultiValueMap<String, String> headerMap) {
		try {

			String url = FLIGHTAPPURL + AIRLINE_REGISTER;
			HttpHeaders headers = new HttpHeaders();
			headers.addAll(headerMap);
			HttpEntity<Airline> httpEntity = new HttpEntity<Airline>(airline, headers);
			ResponseEntity<Airline> response = template.exchange(url, HttpMethod.POST, httpEntity, Airline.class);
			return response;
		} catch (HttpStatusCodeException e) {
			return new ResponseEntity(e.getMessage(), e.getStatusCode());
		} catch (Exception e) {
			return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

}
