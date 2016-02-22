package com.company.scheduler;

import com.company.config.MergerConfig;
import company.scheduler.JobKeyFactory;
import com.google.gson.Gson;
import company.scheduler.QuartzTask;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Trigger;

/**
 * Created by Naya on 05.02.2016.
 */
public class MergeQuartzTask implements QuartzTask {
    private MergerConfig config;
    private Gson gson = new Gson();

    private JobKeyFactory jobKeyFactory;

    public MergeQuartzTask(MergerConfig config, JobKeyFactory jobKeyFactory) {
        this.config = config;
        this.jobKeyFactory = jobKeyFactory;
    }

    @Override
    public JobKey jobKey() {
        return jobKeyFactory.get();
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
        return new MultiTriggerFactory(config, jobKeyFactory).get();
    }
}
