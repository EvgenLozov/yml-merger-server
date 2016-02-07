package com.company.taskscheduler;

import com.company.config.MergerConfig;
import com.google.gson.Gson;
import company.scheduler.QuartzTask;
import org.quartz.*;

import java.util.Date;

import static org.quartz.CalendarIntervalScheduleBuilder.calendarIntervalSchedule;
import static org.quartz.DateBuilder.todayAt;
import static org.quartz.DateBuilder.tomorrowAt;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

/**
 * Created by Naya on 05.02.2016.
 */
public class MergeQuartzTask implements QuartzTask {
    private MergerConfig config;
    private Gson gson = new Gson();

    public MergeQuartzTask(MergerConfig config) {
        this.config = config;
    }

    @Override
    public JobKey jobKey() {
        return JobKey.jobKey(config.getId());
    }

    @Override
    public JobDetail jobDetail() {
        return JobBuilder.newJob(MergeJob.class)
                .withIdentity(jobKey().getName())
                .usingJobData("config", gson.toJson(config))
                .build();
    }

    @Override
    public Trigger trigger() {
        Date startAtDate = null;
        if(config.getNextFireTime()!=0 && (new Date(config.getNextFireTime()).after(new Date())))
            startAtDate = new Date(config.getNextFireTime());

        if (config.getPeriodInHours() > 0) {
            if (startAtDate == null)
                startAtDate = new Date(new Date().getTime() + 3600000 * config.getPeriodInHours());

            TriggerBuilder triggerBuilder = TriggerBuilder
                    .newTrigger()
                    .withIdentity(jobKey().getName())
                    .startAt(startAtDate)
                    .withSchedule(simpleSchedule()
                            .withIntervalInHours(config.getPeriodInHours())
                            .repeatForever());

            return triggerBuilder.build();

        } else {
            if (startAtDate!=null){
                TriggerBuilder triggerBuilder = TriggerBuilder
                        .newTrigger()
                        .withIdentity(jobKey().getName())
                        .startAt(startAtDate)
                        .withSchedule(calendarIntervalSchedule()
                                .withIntervalInDays(config.getPeriod()));
                return triggerBuilder.build();
            }
            else{
                Integer hours = Integer.valueOf(config.getTime().split(":")[0]);
                Integer minutes = Integer.valueOf(config.getTime().split(":")[1]);

                TriggerBuilder triggerBuilder = TriggerBuilder
                        .newTrigger()
                        .withIdentity(jobKey().getName())
                        .withSchedule(calendarIntervalSchedule()
                                .withIntervalInDays(config.getPeriod()));

                if (isBefore(hours, minutes, new Date()))
                    return triggerBuilder.startAt(todayAt(hours, minutes, 0)).build();
                else
                    return triggerBuilder.startAt(tomorrowAt(hours, minutes, 0)).build();
            }
        }
    }

    private boolean isBefore(Integer hours, Integer minutes, Date date) {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(date);
        return hours > cal.get(java.util.Calendar.HOUR_OF_DAY) ||
                (hours == cal.get(java.util.Calendar.HOUR_OF_DAY) && minutes > cal.get(java.util.Calendar.MINUTE));

    }
}
