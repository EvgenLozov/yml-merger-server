package com.company.scheduler;

import com.company.config.MergerConfig;
import company.config.ConfigRepository;
import company.scheduler.InMemoryQuartzTasksScheduler;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;

import java.util.List;

/**
 * Created by Naya on 05.02.2016.
 */
public class InMemoryMergeTaskSchedulerInitializer {

    private MergeQuartzTaskFactory factory = new MergeQuartzTaskFactory();

    private Scheduler scheduler;
    private ConfigRepository<MergerConfig> configRepository;

    public InMemoryMergeTaskSchedulerInitializer(Scheduler scheduler, ConfigRepository<MergerConfig> configRepository) {
        this.scheduler = scheduler;
        this.configRepository = configRepository;
    }

    public InMemoryQuartzTasksScheduler getScheduler() throws SchedulerException {
        InMemoryQuartzTasksScheduler quartzTasksScheduler = new InMemoryQuartzTasksScheduler(scheduler);
        List<MergerConfig> modifierConfigList = configRepository.list();
        for(MergerConfig config: modifierConfigList){
            if(config.isAutoMerge())
                quartzTasksScheduler.schedule(factory.create(config));

        }
        return quartzTasksScheduler;
    }
}
