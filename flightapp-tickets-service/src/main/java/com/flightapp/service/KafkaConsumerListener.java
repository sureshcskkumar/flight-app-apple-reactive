package com.flightapp.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.flightapp.entity.Schedule;

@Service
public class KafkaConsumerListener {

    private static final String TOPIC = "schedule-delete-topic";

    @KafkaListener(topics = TOPIC, groupId="group_id", containerFactory = "userKafkaListenerFactory")
    public void consumeJson(Schedule schedule) {
        System.out.println("Schedule has been cancelled for schedule with id: " + schedule.getId());
        System.out.println("The tickets will soon be deleted and the users will be notified");
        
    }
    
}