package com.merger.scheduler;

import com.company.service.MergeService;
import com.company.service.MergeServiceImpl;
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

    public MergeJobFactory(MergeService mergeService) {
        this.mergeService = mergeService;
    }

    @Override
    public Job newJob(TriggerFiredBundle bundle, Scheduler scheduler) throws SchedulerException {
        MergeJob job = (MergeJob) super.newJob(bundle, scheduler);
        job.setMergeService(mergeService);

        return job;
    }
}
