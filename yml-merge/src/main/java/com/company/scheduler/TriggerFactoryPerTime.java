package com.company.scheduler;

import com.company.config.Config;
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

    private JobKeyFactory jobKeyFactory;

    public TriggerFactoryPerTime(JobKeyFactory jobKeyFactory) {
        this.jobKeyFactory = jobKeyFactory;
    }

    @Override
    public Trigger get(Config config) {
        Integer hours = Integer.valueOf(config.getTime().split(":")[0]);
        Integer minutes = Integer.valueOf(config.getTime().split(":")[1]);

        TriggerBuilder triggerBuilder = TriggerBuilder
                .newTrigger()
                .withIdentity(jobKeyFactory.get(config).getName())
                .withSchedule(calendarIntervalSchedule()
                .withIntervalInDays(config.getPeriod()));

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
