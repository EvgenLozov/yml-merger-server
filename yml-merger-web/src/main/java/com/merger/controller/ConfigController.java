package com.merger.controller;

import com.company.allowedcategories.Category;
import com.company.config.Config;
import com.google.common.collect.Sets;
import com.merger.CategoryRepository;
import com.merger.ConfigRepository;
import com.merger.scheduler.SchedulerService;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.stream.XMLStreamException;
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
    private ConfigRepository configRepository;

    @RequestMapping(method = RequestMethod.GET)
    public List<Config> list() {
        return configRepository.list();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Config get(@PathVariable String id) {
        return configRepository.get(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Config create(@RequestBody Config config) throws SchedulerException {
        Config newConfig = configRepository.create(config);

        if (config.isAutoMerge())
            schedulerService.addTask(newConfig);

        return config;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Config save(@PathVariable String id, @RequestBody Config config) throws SchedulerException {
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

}
