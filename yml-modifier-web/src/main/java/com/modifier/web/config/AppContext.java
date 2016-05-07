package com.modifier.web.config;

import com.company.*;
import com.company.scheduler.InMemoryModifyTaskSchedulerInitializer;
import com.company.scheduler.ModifyJobFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import company.scheduler.InMemoryQuartzTasksScheduler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import company.config.ConfigRepository;
import company.config.JsonConfigRepository;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Yevhen
 */
@Configuration
public class AppContext {

    @Bean
    public ConfigRepository<ModifierConfig> configRepository(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        return new ModifierConfigRepository(new JsonConfigRepository<>("config/config.json",ModifierConfig.class,mapper));
    }

    @Bean
    public InMemoryQuartzTasksScheduler tasksScheduler(ModifyJobFactory jobFactory) throws SchedulerException {
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.setJobFactory(jobFactory);
        scheduler.start();

        InMemoryModifyTaskSchedulerInitializer qtSchedulerInitializer =
                new InMemoryModifyTaskSchedulerInitializer(scheduler, configRepository());

        return qtSchedulerInitializer.getScheduler();
    }

    @Bean
    public ModifierEpocheService epocheService(ConfigRepository<ModifierConfig> configRepository){
        return new ModifierEpocheService(configRepository);
    }

    @Bean
    public EpochalModifyService epochalModifyService(ConfigRepository<ModifierConfig> configRepository){
        return new EpochalModifyService(configRepository, new ModifyService());
    }

    @Bean
    public ModifyJobFactory modifyJobFactory(EpochalModifyService service){
        return new ModifyJobFactory(service);
    }
}
