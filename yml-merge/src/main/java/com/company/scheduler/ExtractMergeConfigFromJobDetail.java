package com.company.scheduler;

import com.company.config.MergerConfig;
import com.google.gson.Gson;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;

/**
 * @author Yevhen
 */
public class ExtractMergeConfigFromJobDetail {

    private Gson gson = new Gson();

    public MergerConfig extract(JobDetail jobDetail){
        JobDataMap dataMap = jobDetail.getJobDataMap();
        String configJson = dataMap.getString("config");

        return gson.fromJson(configJson, MergerConfig.class);
    }
}
