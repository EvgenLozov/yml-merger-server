package com.company.taskscheduler;

import com.company.config.MergerConfig;
import company.config.ConfigRepository;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;

import java.util.List;

/**
 * Created by Naya on 05.02.2016.
 */
public class InMemoryMergeTaskSchedulerInitializer {
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
            if(config.isAutoMerge()==true)
                quartzTasksScheduler.schedule(new MergeQuartzTask(config));

        }
        return quartzTasksScheduler;
    }
}
