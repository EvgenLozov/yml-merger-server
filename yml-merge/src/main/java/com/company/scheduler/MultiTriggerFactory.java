package com.company.scheduler;

import com.company.config.MergerConfig;
import company.scheduler.JobKeyFactory;
import company.scheduler.TriggerFactory;
import org.quartz.Trigger;

/**
 * Created by Yevhen on 2015-08-02.
 */
public class MultiTriggerFactory implements TriggerFactory {

    private MergerConfig config;
    private JobKeyFactory jobKeyFactory;

    public MultiTriggerFactory(MergerConfig config, JobKeyFactory jobKeyFactory) {
        this.config = config;
        this.jobKeyFactory = jobKeyFactory;
    }

    public Trigger get() {
        if (config.getPeriodInHours() > 0) {
            return new TriggerFactoryPerHours(config, jobKeyFactory).get();
        } else {
            return new TriggerFactoryPerTime(config, jobKeyFactory).get();
        }
    }
}
