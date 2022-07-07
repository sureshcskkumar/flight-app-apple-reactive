package com.flightapp.handler;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.flightapp.entity.Airline;
import com.flightapp.repository.AirlineRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class AirlineHandlerUnitTest {

    @MockBean
    private AirlineRepository airlineRepository;
	
    @Autowired
    private WebTestClient webTestClient;


    
    static final String AIRLINES_URL = "/api/v1/airlines";
    
    @Test
    public void getAllAirlinesTest() {
    	List<Airline> airlineList = List.of(
                new Airline(null, "Airline 1", "11111111", false),
                new Airline(null, "Airline 2", "11111112", false),
                new Airline("abcd", "Airline 3", "11111123", false));
    	
    	when(airlineRepository.findAll()).thenReturn(Flux.fromIterable(airlineList));
    	
        webTestClient
        	.get()
        	.uri(AIRLINES_URL)
        	.exchange()
        	.expectStatus()
        	.is2xxSuccessful()
        	.expectBodyList(Airline.class)
        	.value(airlines -> {
            assertEquals(3, airlines.size());
        	});
    }

    
    @Test
    void addAirlineTest() {
        //given
        Airline airline  = new Airline("abc", "Airline 1", "11111111", false);

        when(airlineRepository.save(isA(Airline.class))).thenReturn(Mono.just(new Airline("abc", "Airline 1", "11111111", false)));
        //when
        webTestClient
                .post()
                .uri(AIRLINES_URL)
                .bodyValue(airline)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Airline.class)
                .consumeWith(airlineResponse ->{
                    Airline savedAirline = airlineResponse.getResponseBody();
                    assert savedAirline != null;
                    assertNotNull(savedAirline.getId());
                    assertEquals("abc", savedAirline.getId());

                });

    }
}
