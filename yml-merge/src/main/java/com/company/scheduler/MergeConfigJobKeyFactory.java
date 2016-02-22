package com.company.scheduler;

import com.company.config.MergerConfig;
import company.scheduler.JobKeyFactory;
import org.quartz.JobKey;

/**
 * @author Yevhen
 */
public class MergeConfigJobKeyFactory implements JobKeyFactory {
    private MergerConfig config;

    public MergeConfigJobKeyFactory(MergerConfig config) {
        this.config = config;
    }

    @Override
    public JobKey get() {
        return JobKey.jobKey(config.getId());
    }
}
