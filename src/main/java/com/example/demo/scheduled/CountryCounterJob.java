package com.example.demo.scheduled;

import com.example.demo.service.CountryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CountryCounterJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountryCounterJob.class);

    private final CountryService countryService;

    public CountryCounterJob(CountryService countryService) {
        this.countryService = countryService;
    }

    @Scheduled(fixedDelay = 10000)
    public void recordNumberOfCountries() {
        LOGGER.info("Recorded number of countries on {} : {}", LocalDateTime.now(), countryService.getNumberOfCountries());
    }
}
