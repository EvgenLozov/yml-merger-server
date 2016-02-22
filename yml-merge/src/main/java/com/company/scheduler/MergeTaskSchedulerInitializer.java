package com.company.scheduler;

import com.company.config.MergerConfig;
import company.config.ConfigRepository;
import company.scheduler.QuartzTasksScheduler;
import org.quartz.SchedulerException;

import java.util.List;

/**
 * Created by Naya on 05.02.2016.
 */
public class MergeTaskSchedulerInitializer {

    private MergeQuartzTaskFactory factory = new MergeQuartzTaskFactory();

    private ConfigRepository<MergerConfig> configRepository;

    public MergeTaskSchedulerInitializer(ConfigRepository<MergerConfig> configRepository) {
        this.configRepository = configRepository;
    }

    public void init(QuartzTasksScheduler quartzTasksScheduler) throws SchedulerException {
        List<MergerConfig> modifierConfigList = configRepository.list();
        for(MergerConfig config: modifierConfigList){
            if(config.isAutoMerge())
                quartzTasksScheduler.schedule(factory.create(config));
        }
    }
}
