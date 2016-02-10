package com.company.scheduler;

import com.company.config.MergerConfig;
import com.google.gson.Gson;
import company.config.ConfigRepository;
import org.quartz.*;

/**
 * @author Yevhen
 */
public class PersistMergeJob implements Job {

    private MergeJob mergeJob;
    private Gson gson = new Gson();
    private ConfigRepository<MergerConfig> configRepository;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        String configJson = dataMap.getString("config");
        MergerConfig config = gson.fromJson(configJson, MergerConfig.class);

        config.setNextFireTime(context.getNextFireTime().getTime());
        configRepository.save(config);

        mergeJob.execute(context);
    }

    public void setMergeJob(MergeJob mergeJob) {
        this.mergeJob = mergeJob;
    }

    public void setConfigRepository(ConfigRepository<MergerConfig> configRepository) {
        this.configRepository = configRepository;
    }
}
