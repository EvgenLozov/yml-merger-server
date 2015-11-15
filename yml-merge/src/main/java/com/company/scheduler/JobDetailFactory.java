package com.company.scheduler;

import com.company.config.Config;
import com.google.gson.Gson;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;

/**
 * Created by Yevhen on 2015-08-02.
 */
public class JobDetailFactory {

    private JobKeyFactory jobKeyFactory;
    private Gson gson = new Gson();

    public JobDetailFactory(JobKeyFactory jobKeyFactory) {
        this.jobKeyFactory = jobKeyFactory;
    }

    public JobDetail get(Config config){
        return JobBuilder.newJob(MergeJob.class)
                         .withIdentity(jobKeyFactory.get(config).getName())
                         .usingJobData("config", gson.toJson(config))
                         .build();
    }
}
