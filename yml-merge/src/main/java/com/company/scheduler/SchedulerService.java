package com.company.scheduler;

import com.company.config.Config;
import com.company.logger.ProcessLogger;
import org.quartz.*;

/**
 * Created by Yevhen on 2015-08-02.
 */
public class SchedulerService {
    private static final ProcessLogger logger = ProcessLogger.INSTANCE;

    private Scheduler scheduler;
    private JobKeyFactory jobKeyFactory;
    private JobDetailFactory jobDetailFactory;
    private TriggerFactory triggerFactory;

    public SchedulerService(Scheduler scheduler, JobKeyFactory jobKeyFactory,
                            JobDetailFactory jobDetailFactory, TriggerFactory triggerFactory) {
        this.scheduler = scheduler;
        this.jobKeyFactory = jobKeyFactory;
        this.jobDetailFactory = jobDetailFactory;
        this.triggerFactory = triggerFactory;
    }

    public void addTask(Config config) throws SchedulerException {
        deleteTask(config);

        JobDetail jobDetail = jobDetailFactory.get(config);
        Trigger trigger = triggerFactory.get(config);

        scheduler.scheduleJob(jobDetail, trigger);

        logger.info("Task for auto merge of config " + config.getName() + " is added/updated");
        logger.info("Auto merge will start at : " + trigger.getStartTime());
    }

    public void deleteTask(Config config) throws SchedulerException {
        JobKey jobKey = jobKeyFactory.get(config);

        if (scheduler.checkExists(jobKey)){
            scheduler.deleteJob(jobKey);
            logger.info("Task for auto merge of config " + config.getName() + " is deleted");
        }
    }
}
