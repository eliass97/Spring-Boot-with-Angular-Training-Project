package com.example.demo.service;

import com.example.demo.scheduled.QuartzScheduledJob;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ScheduledService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledService.class);

    private final Scheduler quartzScheduler;
    private final List<? extends QuartzScheduledJob> quartzScheduledJobs;

    @Autowired
    public ScheduledService(final Scheduler quartzScheduler, final List<? extends QuartzScheduledJob> quartzScheduledJobs) {
        this.quartzScheduler = quartzScheduler;
        this.quartzScheduledJobs = quartzScheduledJobs;
    }

    @PostConstruct
    public void init() throws SchedulerException {
        for (final QuartzScheduledJob quartzJob : quartzScheduledJobs) {
            final String jobName = quartzJob.getJobName();
            final String triggerCron = quartzJob.triggerCron();

            LOGGER.info("Proceeding to schedule job with name '{}' (implemented by {}) using cron expression '{}'", jobName, quartzJob.getClass(), triggerCron);

            final JobDetail jobDetail = JobBuilder.newJob(quartzJob.getClass())
                    .withIdentity(jobName)
                    .storeDurably()
                    .build();

            final Set<Trigger> triggers = new HashSet<>();
            final Trigger trigger = TriggerBuilder
                    .newTrigger()
                    .withIdentity(jobName + "Trigger")
                    .withSchedule(CronScheduleBuilder.cronSchedule(triggerCron))
                    .build();

            triggers.add(trigger);

            quartzScheduler.scheduleJob(jobDetail, triggers, true);
        }
    }
}