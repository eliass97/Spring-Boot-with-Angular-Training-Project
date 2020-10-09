package com.example.demo.scheduled;

import com.example.demo.service.CountryService;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CountryCounterJob extends QuartzScheduledJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountryCounterJob.class);

    private final CountryService countryService;

    private final String JOB_NAME = "countryCounterJob";
    private final String CRON_EXPRESSION = "0 0/5 * * * ?";

    public CountryCounterJob(CountryService countryService) {
        this.countryService = countryService;
    }

    @Override
    public void executeInternal(JobExecutionContext jec) {
        LOGGER.info("{} : Recorded number of countries on : {}", LocalDateTime.now(), countryService.getNumberOfCountries());
    }

    public String getJobName() {
        return JOB_NAME;
    }

    public String triggerCron() {
        return CRON_EXPRESSION;
    }
}