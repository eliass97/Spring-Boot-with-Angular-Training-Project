package com.example.demo.scheduled;

import com.example.demo.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PersonCounterJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonCounterJob.class);

    private final PersonService personService;

    public PersonCounterJob(PersonService personService) {
        this.personService = personService;
    }

    @Scheduled(fixedDelay = 10000)
    public void recordNumberOfPersons() {
        LOGGER.info("Recorded number of persons on {} : {}", LocalDateTime.now(), personService.getNumberOfPersons());
    }
}