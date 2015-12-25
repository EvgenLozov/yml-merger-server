package com.merger.controller;

import com.company.config.MergerConfig;
import com.company.logger.ProcessLogger;
import com.company.scheduler.SchedulerService;
import company.config.ConfigRepository;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by Yevhen on 2015-07-14.
 */
@RestController
@RequestMapping("/configs")
public class ConfigController {

    @Autowired
    private SchedulerService schedulerService;

    @Autowired
    private ConfigRepository<MergerConfig> configRepository;

    @RequestMapping(method = RequestMethod.GET)
    public List<MergerConfig> list() {
        return configRepository.list();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public MergerConfig get(@PathVariable String id) {
        return configRepository.get(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public MergerConfig create(@RequestBody MergerConfig config) throws SchedulerException {
        MergerConfig newConfig = configRepository.create(config);

        if (config.isAutoMerge())
            schedulerService.addTask(newConfig);

        return config;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public MergerConfig save(@PathVariable String id, @RequestBody MergerConfig config) throws SchedulerException {
        configRepository.save(config);

        if (config.isAutoMerge())
            schedulerService.addTask(config);
        else
            schedulerService.deleteTask(config);

        return config;
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable String id) throws SchedulerException {
        schedulerService.deleteTask(configRepository.get(id));
        configRepository.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{id}/log", method = RequestMethod.GET)
    public String log(@PathVariable String id) throws SchedulerException {

        return ProcessLogger.INSTANCE.getLog(id);
    }
}
