package com.company.taskscheduler;

import com.company.config.MergerConfig;
import com.company.logger.ProcessLogger;
import com.company.service.MergeService;
import com.google.gson.Gson;
import company.config.ConfigRepository;
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
    private ConfigRepository<MergerConfig> configRepository;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        String configJson = dataMap.getString("config");

        MergerConfig config = gson.fromJson(configJson, MergerConfig.class);

        ProcessLogger.INSTANCE.set(config.getId());

        config.setNextFireTime(context.getNextFireTime().getTime());
        configRepository.save(config);
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

    public void setConfigRepository(ConfigRepository<MergerConfig> configRepository) {
        this.configRepository = configRepository;
    }
}
