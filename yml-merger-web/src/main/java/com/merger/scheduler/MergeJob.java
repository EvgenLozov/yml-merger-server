package com.merger.scheduler;

import com.company.MergeService;
import com.company.config.Config;
import com.google.gson.Gson;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by Yevhen on 2015-08-02.
 */
public class MergeJob implements Job {
    private static final Logger logger = Logger.getLogger(MergeJob.class.getSimpleName());

    private MergeService mergeService;
    private Gson gson = new Gson();

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        String configJson = dataMap.getString("config");

        Config config = gson.fromJson(configJson, Config.class);

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
