package com.company.scheduler;

import com.company.config.MergerConfig;
import org.quartz.JobKey;

/**
 * Created by Yevhen on 2015-08-02.
 */
public class JobKeyFactory {

    public JobKey get(MergerConfig config){
        return JobKey.jobKey(config.getId());
    }
}
