package com.company.scheduler;

import com.company.logger.ProcessLogger;
import com.company.service.MergeService;
import com.company.config.Config;
import com.google.gson.Gson;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

/**
 * Created by Yevhen on 2015-08-02.
 */
public class MergeJob implements Job {
    private static final ProcessLogger logger = ProcessLogger.INSTANCE;

    private MergeService mergeService;
    private Gson gson = new Gson();

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        String configJson = dataMap.getString("config");

        Config config = gson.fromJson(configJson, Config.class);

        ProcessLogger.INSTANCE.set(config.getId());

        try {
            mergeService.process(config);
        } catch (XMLStreamException | IOException e) {
            logger.warning("Unable to do auto merge of " + config.getName());
            e.printStackTrace();
        }
    }

    public void setMergeService(MergeService mergeService) {
        this.mergeService = mergeService;
    }
}
