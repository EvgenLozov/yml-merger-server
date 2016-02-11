package com.merger.config;

import com.company.config.MergerConfig;
import com.company.repository.CategoryRepository;
import com.company.repository.CategorySource;
import com.company.repository.MergerConfigRepository;
import com.company.scheduler.*;
import com.company.service.MergeService;
import com.company.service.MergeServiceImpl;
import com.company.service.SingleProcessMergeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import company.config.ConfigRepository;
import company.config.JsonBasedConfigRepository;
import company.scheduler.InMemoryQuartzTasksScheduler;
import company.scheduler.PersistentQuartzTasksScheduler;
import company.scheduler.QuartzTasksScheduler;
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
    public ConfigRepository<MergerConfig> configRepository(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        return new MergerConfigRepository(new JsonBasedConfigRepository<>("config/config.json",MergerConfig.class,mapper));
    }

    @Bean
    public CategoryRepository categoryRepository(ConfigRepository<MergerConfig> configRepository){
        return new CategoryRepository(new CategorySource(configRepository));
    }

    @Bean
    public MergeService mergeService(ConfigRepository<MergerConfig> configRepository){
        return new SingleProcessMergeService(new MergeServiceImpl(configRepository));
    }
    @Bean
    public NextFireTimeStorage nextFireTimeStorage(ConfigRepository<MergerConfig> configRepository){
        return new NextFireTimeStorage(configRepository);
    }

    @Bean
    public MergeJobFactory mergeJobFactory(MergeService mergeService,NextFireTimeStorage storage){
        return new MergeJobFactory(mergeService, storage);
    }
    @Bean
    public Scheduler taskScheduler(MergeJobFactory mergeJobFactory) throws SchedulerException {
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.setJobFactory(mergeJobFactory);
        scheduler.start();

        return scheduler;
    }

    @Bean
    public QuartzTasksScheduler quartzTasksScheduler(Scheduler scheduler, ConfigRepository<MergerConfig> repository,
                                                     NextFireTimeStorage storage) throws SchedulerException {
        QuartzTasksScheduler inMemoryScheduler = new InMemoryQuartzTasksScheduler(scheduler);
        QuartzTasksScheduler persistentQuartzTasksScheduler = new PersistentQuartzTasksScheduler(inMemoryScheduler, new InConfigFieldQuartzTaskRepository(storage));

        new MergeTaskSchedulerInitializer(repository).init(inMemoryScheduler);

        return persistentQuartzTasksScheduler;
    }
}
