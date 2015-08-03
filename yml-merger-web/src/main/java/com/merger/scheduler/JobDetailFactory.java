package com.merger.scheduler;

import com.company.config.Config;
import org.eclipse.jetty.util.ajax.JSON;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;

/**
 * Created by Yevhen on 2015-08-02.
 */
public class JobDetailFactory {

    private JobKeyFactory jobKeyFactory;

    public JobDetail get(Config config){
        return JobBuilder.newJob(MergeJob.class)
                         .withIdentity(jobKeyFactory.get(config).getName())
                         .usingJobData("config", JSON.toString(config))
                         .build();
    }
}
