package com.company.scheduler;


import com.company.ModifierConfig;
import com.company.logger.ProcessLogger;
import com.google.gson.Gson;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by Naya on 20.01.2016.
 */
public class ModifyJob implements Job {
    private static final ProcessLogger logger = ProcessLogger.INSTANCE;


    private ModifyService modifyService= new ModifyService();
    private Gson gson = new Gson();


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        String configJson = dataMap.getString("config");

        ModifierConfig modifierConfig = gson.fromJson(configJson, ModifierConfig.class);
        ProcessLogger.INSTANCE.set(modifierConfig.getId());

        modifyService.process(modifierConfig);
    }
}
