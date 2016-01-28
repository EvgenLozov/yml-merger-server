package com.company.scheduler;

import com.company.ModifierConfig;
import com.company.taskscheduler.InMemoryQuartzTasksScheduler;
import company.config.ConfigRepository;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;

import java.util.List;

/**
 * Created by Naya on 27.01.2016.
 */
public class InMemoryQTSchedulerInitializer {
    private Scheduler scheduler;
    private ConfigRepository<ModifierConfig> configRepository;

    public InMemoryQTSchedulerInitializer(Scheduler scheduler, ConfigRepository<ModifierConfig> configRepository) {
        this.scheduler = scheduler;
        this.configRepository = configRepository;
    }

    public InMemoryQuartzTasksScheduler getScheduler() throws SchedulerException {
        InMemoryQuartzTasksScheduler quartzTasksScheduler = new InMemoryQuartzTasksScheduler(scheduler);
        List<ModifierConfig> modifierConfigList = configRepository.list();
        for(ModifierConfig config: modifierConfigList){
            if(config.getInputFileURL()!=null)
                quartzTasksScheduler.schedule(new ModifyQuartzTask(config));

        }
        return quartzTasksScheduler;
    }
}
