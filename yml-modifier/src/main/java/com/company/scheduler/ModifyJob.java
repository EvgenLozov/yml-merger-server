package com.company.scheduler;


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
    private ModifyService modifyService= new ModifyService();
    private Gson gson = new Gson();


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        String configJson = dataMap.getString("config");

        ModifierConfig modifierConfig = gson.fromJson(configJson, ModifierConfig.class);

        modifyService.process(modifierConfig);
    }
}
