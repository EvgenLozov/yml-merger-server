package com.modifier.web.config;

import com.company.ModifierConfig;
import com.company.scheduler.InMemoryQTSchedulerInitializer;
import com.company.taskscheduler.InMemoryQuartzTasksScheduler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import company.config.ConfigRepository;
import company.config.JsonBasedConfigRepository;
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

        return new JsonBasedConfigRepository<>("config/config.json",ModifierConfig.class,mapper);
    }

    @Bean
    public InMemoryQuartzTasksScheduler tasksScheduler() throws SchedulerException {
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.start();
        InMemoryQTSchedulerInitializer qtSchedulerInitializer = new InMemoryQTSchedulerInitializer(scheduler, configRepository());

        return qtSchedulerInitializer.getScheduler();
    }

}
