package com.merger.config;

import com.company.config.MergerConfig;
import com.company.repository.CategoryRepository;
import com.company.repository.CategorySource;
import com.company.repository.MergerConfigRepository;
import com.company.scheduler.*;
import com.company.service.MergeService;
import com.company.service.MergeServiceImpl;
import com.company.service.SingleProcessMergeService;
import com.company.taskscheduler.InMemoryMergeTaskSchedulerInitializer;
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
    public ConfigRepository configRepository(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        return new MergerConfigRepository(new JsonBasedConfigRepository<>("config/config.json",MergerConfig.class,mapper));
    }

    @Bean
    public CategoryRepository categoryRepository(ConfigRepository configRepository){
        return new CategoryRepository(new CategorySource(configRepository));
    }

    @Bean
    public MergeService mergeService(ConfigRepository configRepository){
        return new SingleProcessMergeService(new MergeServiceImpl(configRepository));
    }
    @Bean
    public InMemoryQuartzTasksScheduler taskScheduler(ConfigRepository configRepository) throws SchedulerException {
        MergeJobFactory mergeJobFactory = new MergeJobFactory(mergeService(configRepository), configRepository);
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.setJobFactory(mergeJobFactory);
        scheduler.start();
        InMemoryMergeTaskSchedulerInitializer schedulerInitializer =
                new InMemoryMergeTaskSchedulerInitializer(scheduler, configRepository());

        return schedulerInitializer.getScheduler();
    }
}
