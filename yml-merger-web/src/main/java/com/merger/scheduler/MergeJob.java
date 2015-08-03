package com.merger.scheduler;

import com.company.MergeService;
import com.company.config.Config;
import org.eclipse.jetty.util.ajax.JSON;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;

/**
 * Created by Yevhen on 2015-08-02.
 */
public class MergeJob implements Job {

    private MergeService mergeService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        String configJson = dataMap.getString("config");

        Config config = (Config) JSON.parse(configJson);

        try {
            mergeService.process(config);
        } catch (FileNotFoundException | XMLStreamException e) {
            e.printStackTrace();
        }
    }

    public void setMergeService(MergeService mergeService) {
        this.mergeService = mergeService;
    }
}
