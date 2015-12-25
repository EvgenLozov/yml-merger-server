package com.company.scheduler;

import com.company.config.MergerConfig;
import org.quartz.Trigger;

/**
 * Created by Yevhen on 2015-08-02.
 */
public class MultiTriggerFactory implements TriggerFactory{

    private JobKeyFactory jobKeyFactory;

    public MultiTriggerFactory(JobKeyFactory jobKeyFactory) {
        this.jobKeyFactory = jobKeyFactory;
    }

    public Trigger get(MergerConfig config) {
        if (config.getPeriodInHours() > 0) {
            return new TriggerFactoryPerHours(jobKeyFactory).get(config);
        } else {
            return new TriggerFactoryPerTime(jobKeyFactory).get(config);
        }
    }
}
