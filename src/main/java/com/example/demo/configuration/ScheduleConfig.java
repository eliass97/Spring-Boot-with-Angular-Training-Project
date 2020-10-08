package com.example.demo.configuration;

import com.example.demo.service.CountryService;
import com.example.demo.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

@Configuration
@EnableScheduling
public class ScheduleConfig {

    private final PersonService personService;
    private final CountryService countryService;

    public ScheduleConfig(PersonService personService, CountryService countryService) {
        this.personService = personService;
        this.countryService = countryService;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(CountryService.class);

    @Scheduled(fixedDelay = 3000000)
    public void LogNumberOfPersons() {
        LOGGER.info("Recorded number of persons on {} : {}", LocalDateTime.now(), personService.getNumberOfPersons());
    }

    @Scheduled(fixedDelay = 3000000)
    public void LogNumberOfCountries() {
        LOGGER.info("Recorded number of countries on {} : {}", LocalDateTime.now(), countryService.getNumberOfCountries());
    }
}
