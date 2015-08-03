package com.merger.scheduler;

import com.company.config.Config;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.spi.JobFactory;

/**
 * Created by Yevhen on 2015-08-02.
 */
public class SchedulerService {

    private Scheduler scheduler;
    private JobKeyFactory jobKeyFactory;
    private JobDetailFactory jobDetailFactory;
    private TriggerFactory triggerFactory;

    public void addTask(Config config) throws SchedulerException {
        JobKey jobKey = jobKeyFactory.get(config);

        if (scheduler.checkExists(jobKey)){
            scheduler.deleteJob(jobKey);
        }

        scheduler.scheduleJob(jobDetailFactory.get(config), triggerFactory.get(config));
    }
}
