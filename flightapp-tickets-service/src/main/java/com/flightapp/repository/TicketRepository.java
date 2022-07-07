package com.flightapp.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.flightapp.entity.Ticket;

public interface TicketRepository extends ReactiveMongoRepository<Ticket, String> {

}
