package com.company.scheduler;

import com.company.config.MergerConfig;
import company.scheduler.JobKeyFactory;
import company.scheduler.TriggerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import java.util.Calendar;
import java.util.Date;

import static org.quartz.CalendarIntervalScheduleBuilder.calendarIntervalSchedule;
import static org.quartz.DateBuilder.todayAt;
import static org.quartz.DateBuilder.tomorrowAt;

/**
 * @author Yevhen
 */
public class TriggerFactoryPerTime implements TriggerFactory {

    private MergerConfig config;
    private JobKeyFactory jobKeyFactory;

    public TriggerFactoryPerTime(MergerConfig config, JobKeyFactory jobKeyFactory) {
        this.config = config;
        this.jobKeyFactory = jobKeyFactory;
    }

    @Override
    public Trigger get() {
        Integer hours = Integer.valueOf(config.getTime().split(":")[0]);
        Integer minutes = Integer.valueOf(config.getTime().split(":")[1]);

        TriggerBuilder triggerBuilder = TriggerBuilder
                .newTrigger()
                .withIdentity(jobKeyFactory.get().getName())
                .withSchedule(calendarIntervalSchedule()
                .withIntervalInDays(config.getPeriod()));

        if (config.getNextFireTime() > 0 && (new Date(config.getNextFireTime()).after(new Date())))
            return triggerBuilder.startAt(new Date(config.getNextFireTime())).build();

        if (isBefore(hours, minutes, new Date()))
            return triggerBuilder.startAt(todayAt(hours, minutes, 0)).build();
        else
            return triggerBuilder.startAt(tomorrowAt(hours, minutes, 0)).build();
    }

    private boolean isBefore(Integer hours, Integer minutes, Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return hours > cal.get(Calendar.HOUR_OF_DAY) ||
                (hours == cal.get(Calendar.HOUR_OF_DAY) && minutes > cal.get(Calendar.MINUTE));

    }
}
