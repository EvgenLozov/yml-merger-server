package com.merger;

import com.company.repository.CategoryRepository;
import com.company.repository.CategorySource;
import com.company.repository.ConfigRepository;
import com.company.service.MergeService;
import com.company.service.MergeServiceImpl;
import com.company.service.SingleProcessMergeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.company.scheduler.*;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Created by Yevhen on 2015-06-28.
 */

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public ConfigRepository configRepository(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        return new ConfigRepository(mapper);
    }

    @Bean
    public CategoryRepository categoryRepository(){
        return new CategoryRepository(new CategorySource(configRepository()));
    }

    @Bean
    public MergeService mergeService(){
        return new SingleProcessMergeService(new MergeServiceImpl(configRepository()));
    }
    @Bean
    public SchedulerService schedulerService() throws SchedulerException {
        JobKeyFactory jobKeyFactory = new JobKeyFactory();
        JobDetailFactory jobDetailFactory = new JobDetailFactory(jobKeyFactory);
        TriggerFactory triggerFactory = new TriggerFactory(jobKeyFactory);

        MergeJobFactory mergeJobFactory = new MergeJobFactory(mergeService());
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.setJobFactory(mergeJobFactory);
        scheduler.start();

        return new SchedulerService(scheduler, jobKeyFactory, jobDetailFactory, triggerFactory);
    }
}