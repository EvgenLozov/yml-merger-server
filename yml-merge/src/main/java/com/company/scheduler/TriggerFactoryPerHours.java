package com.company.scheduler;

import com.company.config.MergerConfig;
import company.scheduler.JobKeyFactory;
import company.scheduler.TriggerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import java.util.Date;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

/**
 * @author Yevhen
 */
public class TriggerFactoryPerHours implements TriggerFactory {
    private MergerConfig config;
    private JobKeyFactory jobKeyFactory;

    public TriggerFactoryPerHours(MergerConfig config, JobKeyFactory jobKeyFactory) {
        this.config = config;
        this.jobKeyFactory = jobKeyFactory;
    }

    @Override
    public Trigger get() {
        Date startAtDate;
        if (config.getNextFireTime() <= 0 || (new Date(config.getNextFireTime()).before(new Date())))
             startAtDate = new Date(new Date().getTime() + 3600000 * config.getPeriodInHours());
        else
            startAtDate = new Date(config.getNextFireTime());

        TriggerBuilder triggerBuilder = TriggerBuilder
                .newTrigger()
                .withIdentity(jobKeyFactory.get().getName())
                .startAt(startAtDate)
                .withSchedule(simpleSchedule()
                .withIntervalInHours(config.getPeriodInHours())
                .repeatForever());

        return triggerBuilder.build();
    }
}
