package com.company.scheduler;

import com.company.service.MergeService;
import com.company.taskscheduler.MergeJob;
import company.config.ConfigRepository;
import org.quartz.Job;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.simpl.SimpleJobFactory;
import org.quartz.spi.TriggerFiredBundle;

/**
 * Created by Yevhen on 2015-08-03.
 */
public class MergeJobFactory extends SimpleJobFactory{

    private MergeService mergeService;
    private ConfigRepository configRepository;

    public MergeJobFactory(MergeService mergeService, ConfigRepository configRepository) {
        this.mergeService = mergeService;
        this.configRepository = configRepository;
    }

    @Override
    public Job newJob(TriggerFiredBundle bundle, Scheduler scheduler) throws SchedulerException {
        MergeJob job = (MergeJob) super.newJob(bundle, scheduler);
        job.setMergeService(mergeService);
        job.setConfigRepository(configRepository);

        return job;
    }
}
