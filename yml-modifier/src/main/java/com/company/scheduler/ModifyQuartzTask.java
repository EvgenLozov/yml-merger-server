package com.company.scheduler;

import com.company.ModifierConfig;
import com.google.gson.Gson;
import company.scheduler.QuartzTask;
import org.quartz.*;

import java.util.Date;

import static org.quartz.CalendarIntervalScheduleBuilder.calendarIntervalSchedule;
import static org.quartz.DateBuilder.todayAt;
import static org.quartz.DateBuilder.tomorrowAt;

/**
 * Created by Naya on 21.01.2016.
 */
public class ModifyQuartzTask implements QuartzTask {


    private ModifierConfig config;
    private Gson gson = new Gson();

    public ModifyQuartzTask(ModifierConfig config){
        this.config = config;
    }

    @Override
    public JobKey jobKey() {
        return JobKey.jobKey(config.getId());
    }

    @Override
    public JobDetail jobDetail() {
        return JobBuilder.newJob(ModifyJob.class)
                .withIdentity(jobKey().getName())
                .usingJobData("config", gson.toJson(config))
                .build();
    }

    @Override
    public Trigger trigger() {
        Integer hours = Integer.valueOf(config.getTime().split(":")[0]);
        Integer minutes = Integer.valueOf(config.getTime().split(":")[1]);

        TriggerBuilder triggerBuilder = TriggerBuilder
                .newTrigger()
                .withIdentity(jobKey().getName())
                .withSchedule(calendarIntervalSchedule()
                        .withIntervalInDays(config.
                                getPeriod()));

        if (isBefore(hours, minutes, new Date()))
            return triggerBuilder.startAt(todayAt(hours, minutes, 0)).build();
        else
            return triggerBuilder.startAt(tomorrowAt(hours, minutes, 0)).build();
    }

    private boolean isBefore(Integer hours, Integer minutes, Date date) {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(date);
        return hours > cal.get(java.util.Calendar.HOUR_OF_DAY) ||
                (hours == cal.get(java.util.Calendar.HOUR_OF_DAY) && minutes > cal.get(java.util.Calendar.MINUTE));

    }
}
