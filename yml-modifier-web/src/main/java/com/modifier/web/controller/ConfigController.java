package com.modifier.web.controller;

import com.company.ModifierConfig;
import com.company.ModifierConfigValidator;
import com.company.scheduler.ModifyQuartzTask;
import company.config.Validator;
import company.scheduler.InMemoryQuartzTasksScheduler;
import company.config.ConfigRepository;
import org.quartz.SchedulerException;
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

    private Validator<ModifierConfig> validator = new ModifierConfigValidator();

    @RequestMapping(method = RequestMethod.GET)
    public List<ModifierConfig> list() {
        List<ModifierConfig> configs = configRepository.list();
        return configs;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModifierConfig get(@PathVariable String id) {
        ModifierConfig config = configRepository.get(id);
        return config;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModifierConfig create(@RequestBody ModifierConfig config) throws SchedulerException {
        validator.validate(config);
        configRepository.create(config);

        if(config.getInputFileURL()!=null && !config.getInputFileURL().isEmpty()) {
            tasksScheduler.schedule(new ModifyQuartzTask(config));
        }

        return configRepository.get(config.getId());

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ModifierConfig save(@PathVariable String id, @RequestBody ModifierConfig config) throws SchedulerException {
        validator.validate(config);
        configRepository.save(config);

        ModifyQuartzTask task = new ModifyQuartzTask(config);

        if(config.getInputFileURL()!=null && !config.getInputFileURL().isEmpty()){
            if (tasksScheduler.isScheduled(task))
                    tasksScheduler.delete(task);
            tasksScheduler.schedule(task);}
        else
            tasksScheduler.delete(task);

        return configRepository.get(config.getId());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable String id) throws SchedulerException {
        tasksScheduler.delete(new ModifyQuartzTask(configRepository.get(id)));
        configRepository.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
