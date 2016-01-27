package com.modifier.web.controller;

import com.company.ModifierConfig;
import com.company.scheduler.ModifyQuartzTask;
import com.company.taskscheduler.InMemoryQuartzTasksScheduler;
import com.company.taskscheduler.QuartzTask;
import company.config.ConfigRepository;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Naya on 13.01.2016.
 */
@RestController
@RequestMapping("/configs")
public class ConfigController {

    @Autowired
    private InMemoryQuartzTasksScheduler tasksScheduler;

    @Autowired
    private ConfigRepository<ModifierConfig> configRepository;

    @RequestMapping(method = RequestMethod.GET)
    public List<ModifierConfig> list() {
        return configRepository.list();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModifierConfig get(@PathVariable String id) {
        return configRepository.get(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModifierConfig create(@RequestBody ModifierConfig config) throws SchedulerException {
        configRepository.create(config);

        if(config.getInputFileURL()!=null)
            tasksScheduler.schedule(new ModifyQuartzTask(config));

        return config;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ModifierConfig save(@PathVariable String id, @RequestBody ModifierConfig config) throws SchedulerException {
        configRepository.save(config);
        ModifyQuartzTask task = new ModifyQuartzTask(config);

        if(config.getInputFileURL()!=null){
            if (tasksScheduler.isScheduled(task))
                    tasksScheduler.delete(task);
            tasksScheduler.schedule(task);}
        else
            tasksScheduler.delete(task);
        return config;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable String id) throws SchedulerException {
        tasksScheduler.delete(new ModifyQuartzTask(configRepository.get(id)));
        configRepository.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
