package com.merger.scheduler;

import com.company.config.Config;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import java.util.logging.Logger;

import static org.quartz.CalendarIntervalScheduleBuilder.calendarIntervalSchedule;
import static org.quartz.DateBuilder.todayAt;

/**
 * Created by Yevhen on 2015-08-02.
 */
public class TriggerFactory {

    private JobKeyFactory jobKeyFactory;

    public TriggerFactory(JobKeyFactory jobKeyFactory) {
        this.jobKeyFactory = jobKeyFactory;
    }

    public Trigger get(Config config){
        Integer hours = Integer.valueOf(config.getTime().split(":")[0]);
        Integer minutes = Integer.valueOf(config.getTime().split(":")[1]);

        return TriggerBuilder
                .newTrigger()
                .withIdentity(jobKeyFactory.get(config).getName())
                .startAt(todayAt(hours, minutes, 0))
                .withSchedule(calendarIntervalSchedule().withIntervalInDays(config.getPeriod()))
                .build();
    }
}
