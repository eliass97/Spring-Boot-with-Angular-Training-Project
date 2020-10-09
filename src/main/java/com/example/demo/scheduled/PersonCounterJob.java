package com.example.demo.scheduled;

import com.example.demo.service.PersonService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@DisallowConcurrentExecution
public class PersonCounterJob extends QuartzScheduledJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonCounterJob.class);

    private final PersonService personService;

    private final String JOB_NAME = "personCounterJob";
    private final String CRON_EXPRESSION = "0 0/10 * * * ?";

    public PersonCounterJob(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public void executeInternal(JobExecutionContext jec) {
        LOGGER.info("{} : Recorded number of persons : {}", LocalDateTime.now(), personService.getNumberOfPersons());
    }

    public String getJobName() {
        return JOB_NAME;
    }

    public String triggerCron() {
        return CRON_EXPRESSION;
    }
}