package com.flightapp.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.flightapp.entity.Airline;
import com.flightapp.repository.AirlineRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
public class AirlineHandlerIntegrationTest {
	
    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private AirlineRepository airlineRepository;
    
    static final String AIRLINESS_URL = "/api/v1/airlines";
    
    @BeforeEach
    void setUp() {
    	List<Airline> airlines = List.of(
                new Airline(null, "Airline 1", "11111111", false),
                new Airline(null, "Airline 2", "11111112", false),
                new Airline("abcd", "Airline 3", "11111123", false));
        airlineRepository.saveAll(airlines)
                .blockLast();
    }

    @AfterEach
    void tearDown() {
        airlineRepository.deleteAll()
                .block();
    }
    
    @Test
    void getAllAirlinesTest() {
        webTestClient
                .get()
                .uri(AIRLINESS_URL)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(Airline.class)
                .value(airlines -> {
                    assertEquals(4, airlines.size());
                });
    }

    @Test
    void getAirlineByIdTest() {
        webTestClient
                .get()
                .uri(AIRLINESS_URL + "/abcd")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(Airline.class)
                .value(airline -> {
                    assertEquals(airline.getName(), "Airline 3");
                });
    }
    
    @Test
    void addAirlineTest() {
        Airline airline = new Airline(null, "Airline 4", "11111234", false);
        webTestClient
                .post()
                .uri(AIRLINESS_URL)
                .bodyValue(airline)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Airline.class)
                .consumeWith(airlineResponse -> {
                    Airline savedAirline = airlineResponse.getResponseBody();
                    assert savedAirline != null;
                    assertNotNull(savedAirline.getId());
                });

    }
    
}
