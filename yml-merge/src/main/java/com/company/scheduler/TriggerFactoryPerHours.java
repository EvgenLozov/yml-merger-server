package com.company.scheduler;

import com.company.config.Config;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import java.util.Date;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

/**
 * @author Yevhen
 */
public class TriggerFactoryPerHours implements TriggerFactory {

    private JobKeyFactory jobKeyFactory;

    public TriggerFactoryPerHours(JobKeyFactory jobKeyFactory) {
        this.jobKeyFactory = jobKeyFactory;
    }

    @Override
    public Trigger get(Config config) {
        Date startAtDate = new Date(new Date().getTime() + 3600000 * config.getPeriodInHours());

        TriggerBuilder triggerBuilder = TriggerBuilder
                .newTrigger()
                .withIdentity(jobKeyFactory.get(config).getName())
                .startAt(startAtDate)
                .withSchedule(simpleSchedule()
                .withIntervalInHours(config.getPeriodInHours())
                .repeatForever());

        return triggerBuilder.build();
    }
}
