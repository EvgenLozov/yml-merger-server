package com.company.scheduler;

import com.company.service.MergeService;
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
    private NextFireTimeStorage storage;

    public MergeJobFactory(MergeService mergeService, NextFireTimeStorage storage) {
        this.mergeService = mergeService;
        this.storage = storage;
    }

    @Override
    public Job newJob(TriggerFiredBundle bundle, Scheduler scheduler) throws SchedulerException {
        MergeJob job = (MergeJob) super.newJob(bundle, scheduler);
        job.setMergeService(mergeService);
        job.setStorage(storage);

        return job;
    }
}
