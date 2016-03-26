package com.company.scheduler;


import com.company.EpochalModifyService;
import com.company.ModifierConfig;
import com.company.ModifyService;
import com.google.gson.Gson;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by Naya on 20.01.2016.
 */
public class ModifyJob implements Job {
    private EpochalModifyService service;

    public ModifyJob() {
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        service.process(dataMap.getString("configId"));
    }

    public void setService(EpochalModifyService service) {
        this.service = service;
    }
}
