package com.company.scheduler;

import com.company.ModifierConfig;
import company.config.ConfigRepository;
import company.scheduler.InMemoryQuartzTasksScheduler;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;

import java.util.List;

/**
 * Created by Naya on 27.01.2016.
 */
public class InMemoryModifyTaskSchedulerInitializer {
    private Scheduler scheduler;
    private ConfigRepository<ModifierConfig> configRepository;

    public InMemoryModifyTaskSchedulerInitializer(Scheduler scheduler, ConfigRepository<ModifierConfig> configRepository) {
        this.scheduler = scheduler;
        this.configRepository = configRepository;
    }

    public InMemoryQuartzTasksScheduler getScheduler() throws SchedulerException {
        InMemoryQuartzTasksScheduler quartzTasksScheduler = new InMemoryQuartzTasksScheduler(scheduler);
        List<ModifierConfig> modifierConfigList = configRepository.list();
        for(ModifierConfig config: modifierConfigList){
            if(config.getInputFileURL()!=null && !config.getInputFileURL().isEmpty())
                quartzTasksScheduler.schedule(new ModifyQuartzTask(config));

        }
        return quartzTasksScheduler;
    }
}
