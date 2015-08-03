package com.merger.scheduler;

import com.company.config.Config;
import org.quartz.JobKey;

/**
 * Created by Yevhen on 2015-08-02.
 */
public class JobKeyFactory {

    public JobKey get(Config config){
        return JobKey.jobKey(config.getId());
    }
}
