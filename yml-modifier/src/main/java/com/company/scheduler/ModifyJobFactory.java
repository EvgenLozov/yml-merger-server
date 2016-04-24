package com.company.scheduler;

import com.company.EpochalModifyService;
import org.quartz.Job;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.simpl.SimpleJobFactory;
import org.quartz.spi.TriggerFiredBundle;

public class ModifyJobFactory extends SimpleJobFactory {

    private EpochalModifyService service;

    public ModifyJobFactory(EpochalModifyService service) {
        this.service = service;
    }

    @Override
    public Job newJob(TriggerFiredBundle bundle, Scheduler scheduler) throws SchedulerException {
        ModifyJob modifyJob = (ModifyJob) super.newJob(bundle, scheduler);
        modifyJob.setService(service);

        return modifyJob;
    }
}
